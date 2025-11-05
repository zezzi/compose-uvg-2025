package com.zezziapp.testapp.features.meals.data.repository

import com.zezziapp.testapp.remote.domain.MealCategory

interface MealRepository {
    suspend fun fetchCategories(): List<MealCategory>
}