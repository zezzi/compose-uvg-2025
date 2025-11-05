package com.zezziapp.testapp.remote.di

import com.zezziapp.testapp.remote.MealDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit

//@Module
//@InstallIn(SingletonComponent::class)
//object MealDbModule {
//    @Provides
//    @Singleton
//    fun provideMealDbApi(retrofit: Retrofit): MealDbApi =
//        retrofit.create(MealDbApi::class.java)
//}