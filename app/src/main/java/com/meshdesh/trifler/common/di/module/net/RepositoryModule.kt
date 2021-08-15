package com.meshdesh.trifler.common.di.module.net

import com.meshdesh.trifler.category.data.repository.CategoryRepository
import com.meshdesh.trifler.category.data.repository.CategoryRepositoryImpl
import com.meshdesh.trifler.common.auth.repository.TokenRepository
import com.meshdesh.trifler.common.auth.repository.TokenRepositoryImpl
import com.meshdesh.trifler.contact.repository.ContactRepository
import com.meshdesh.trifler.contact.repository.ContactRepositoryImpl
import com.meshdesh.trifler.login.data.repository.LoginRepository
import com.meshdesh.trifler.login.data.repository.LoginRepositoryImpl
import com.meshdesh.trifler.signup.data.repository.SignupRepository
import com.meshdesh.trifler.signup.data.repository.SignupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesTokenRepository(
        tokenRepository: TokenRepositoryImpl
    ): TokenRepository

    @Singleton
    @Binds
    abstract fun providesSignupRepository(
        signupRepository: SignupRepositoryImpl
    ): SignupRepository

    @Singleton
    @Binds
    abstract fun providesLoginRepository(
        loginRepository: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun bindsCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Singleton
    @Binds
    abstract fun bindsContactRepository(
        contactRepositoryImpl: ContactRepositoryImpl
    ): ContactRepository
}