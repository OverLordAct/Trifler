package com.meshdesh.trifler.common.di.module.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meshdesh.trifler.common.auth.AccessTokenAuthenticator
import com.meshdesh.trifler.common.data.api.TokenAPI
import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter.CoroutinesCallAdapterFactory
import com.meshdesh.trifler.common.di.qualifier.AuthenticatedClient
import com.meshdesh.trifler.common.di.qualifier.BaseClient
import com.meshdesh.trifler.common.di.qualifier.CoroutinesAPI
import com.meshdesh.trifler.common.di.qualifier.LoggingInterceptor
import com.meshdesh.trifler.common.di.qualifier.NonCoroutinesAPI
import com.meshdesh.trifler.common.di.qualifier.TokenInterceptor
import com.meshdesh.trifler.common.di.qualifier.UnauthenticatedAPI
import com.meshdesh.trifler.common.storage.token.TokenManager
import com.meshdesh.trifler.common.storage.token.TokenManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val baseUrl = "https://trifler.herokuapp.com"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @CoroutinesAPI
    @Singleton
    @Provides
    fun providesRetrofitBuilderWithCoroutines(
        @AuthenticatedClient okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutinesCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @NonCoroutinesAPI
    @Singleton
    @Provides
    fun providesRetrofitBuilderWithoutCoroutines(
        @BaseClient okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @UnauthenticatedAPI
    @Singleton
    @Provides
    fun providesUnauthenticatedRetrofitBuilderWithCoroutines(
        @BaseClient okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutinesCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun providesUnauthenticatedTriflerAPI(@UnauthenticatedAPI retrofit: Retrofit.Builder): TriflerAPI.UnauthenticatedAPI {
        return retrofit.build().create(TriflerAPI.UnauthenticatedAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTriflerAPI(@CoroutinesAPI retrofit: Retrofit.Builder): TriflerAPI {
        return retrofit.build().create(TriflerAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTokenAPI(
        @NonCoroutinesAPI retrofit: Retrofit.Builder
    ): TokenAPI {
        return retrofit.build().create(TokenAPI::class.java)
    }

    @BaseClient
    @Provides
    fun providesBaseClient(
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @AuthenticatedClient
    @Provides
    fun providesAuthenticatedClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @TokenInterceptor tokenInterceptor: Interceptor,
        authenticator: AccessTokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @LoggingInterceptor
    @Provides
    fun providesLoggingInterceptor(): Interceptor {
        val client = OkHttpClient.Builder()

        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(httpInterceptor)

        return httpInterceptor
    }

    @TokenInterceptor
    @Provides
    fun providesHeaderInterceptor(tokenManager: TokenManager): Interceptor {
        return Interceptor {
            it.proceed(
                it.request().newBuilder()
                    .header(
                        AccessTokenAuthenticator.AUTHENTICATION,
                        tokenManager.getAccessToken() ?: ""
                    )
                    .build()
            )
        }
    }

    @Singleton
    @Provides
    fun providesTokenManager(tokenManager: TokenManagerImpl): TokenManager {
        return tokenManager
    }
}