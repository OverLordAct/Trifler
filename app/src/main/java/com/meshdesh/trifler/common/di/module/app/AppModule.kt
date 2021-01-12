package com.meshdesh.trifler.common.di.module.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meshdesh.trifler.common.auth.AccessTokenAuthenticator
import com.meshdesh.trifler.common.data.api.TokenAPI
import com.meshdesh.trifler.common.data.api.TriflerAPI
import com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter.CoroutinesCallAdapterFactory
import com.meshdesh.trifler.common.di.qualifier.LoggingInterceptor
import com.meshdesh.trifler.common.di.qualifier.TokenInterceptor
import com.meshdesh.trifler.common.storage.token.TokenManager
import com.meshdesh.trifler.common.storage.token.TokenManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    private val baseUrl = "https://trifler.herokuapp.com"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutinesCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun providesTriflerAPI(retrofit: Retrofit.Builder): TriflerAPI {
        return retrofit.build().create(TriflerAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTokenAPI(
        retrofit: Retrofit.Builder
    ): TokenAPI {
        return retrofit.build().create(TokenAPI::class.java)
    }

    @Provides
    fun providesHttpLoggerClient(
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
                    .header("Authorization", tokenManager.getAccessToken() ?: "")
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