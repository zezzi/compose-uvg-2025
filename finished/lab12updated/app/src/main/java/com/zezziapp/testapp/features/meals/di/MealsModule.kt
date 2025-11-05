package com.zezziapp.testapp.features.meals.di

import com.zezziapp.testapp.features.meals.data.repository.MealRepository
import com.zezziapp.testapp.features.meals.data.repository.MealRepositoryImpl
import com.zezziapp.testapp.features.meals.domain.usecases.GetMealCategories
import com.zezziapp.testapp.features.meals.domain.usecases.SearchMealCategories
import com.zezziapp.testapp.remote.MealDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealsModule {
    @Provides
    @Singleton fun repo(api: MealDbApi): MealRepository = MealRepositoryImpl(api)
    @Provides
    @Singleton fun getMeals(repo: MealRepository) = GetMealCategories(repo)
    @Provides
    @Singleton fun searchMeals() = SearchMealCategories()
}