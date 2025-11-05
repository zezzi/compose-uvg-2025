package com.zezziapp.testapp.features.meals.domain.usecases

import com.zezziapp.testapp.remote.domain.MealCategory
import kotlin.collections.filter

class SearchMealCategories {
    operator fun invoke(q: String, data: List<MealCategory>): List<MealCategory> =
        if (q.isBlank()) data else data.filter { it.name.contains(q, ignoreCase = true) }
}