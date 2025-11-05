package com.zezziapp.testapp.features.auth.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.zezziapp.testapp.features.auth.data.AuthRepository
import com.zezziapp.testapp.features.auth.data.AuthRepositoryImpl
import com.zezziapp.testapp.features.auth.domain.usecase.LoginUser
import com.zezziapp.testapp.features.auth.domain.usecase.RegisterUser

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideFirebaseAuth(app: Application): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)

    @Provides
    fun provideLoginUser(repo: AuthRepository) = LoginUser(repo)

    @Provides
    fun provideRegisterUser(repo: AuthRepository) = RegisterUser(repo)
}
