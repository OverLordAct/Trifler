package com.meshdesh.trifler.common.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutinesAPI

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NonCoroutinesAPI