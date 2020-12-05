package com.meshdesh.trifler.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meshdesh.trifler.common.data.TokenAuthenticator
import com.meshdesh.trifler.common.data.TriflerAPI
import com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter.CoroutinesCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    private val baseUrl = "https://trifler.herokuapp.com"

    // TODO

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
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

    @Provides
    fun providesHttpClient(tokenAuthenticator: TokenAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .build()
    }
}