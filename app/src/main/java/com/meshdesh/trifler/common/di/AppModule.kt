package com.meshdesh.trifler.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meshdesh.trifler.common.data.calladapters.coroutinecalladapter.CoroutinesCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    // TODO Add baseUrl
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutinesCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }
}