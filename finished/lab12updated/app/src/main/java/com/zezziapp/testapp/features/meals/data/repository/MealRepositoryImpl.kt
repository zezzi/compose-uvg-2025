package com.zezziapp.testapp.features.meals.data.repository

import com.zezziapp.testapp.remote.MealDbApi
import com.zezziapp.testapp.remote.domain.MealCategory
import com.zezziapp.testapp.remote.mapper.toDomain

class MealRepositoryImpl(
    private val api: MealDbApi
) : MealRepository {
    override suspend fun fetchCategories(): List<MealCategory> =
        api.getCategories().categories.map {
            it.toDomain()
        }
}