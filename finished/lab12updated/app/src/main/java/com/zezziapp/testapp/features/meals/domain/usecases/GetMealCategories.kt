package com.zezziapp.testapp.features.meals.domain.usecases

import com.zezziapp.testapp.features.meals.data.repository.MealRepository
import com.zezziapp.testapp.remote.domain.MealCategory
import  com.zezziapp.testapp.core.util.Result

class GetMealCategories(private val repo: MealRepository) {
    suspend operator fun invoke(): Result<List<MealCategory>> =
        try { Result.Ok(repo.fetchCategories()) }
        catch (t: Throwable) { Result.Err(t) }
}