package com.zezziapp.testapp.remote.mapper

import com.zezziapp.testapp.remote.domain.MealCategory
import com.zezziapp.testapp.remote.dto.CategoryDto

fun CategoryDto.toDomain() = MealCategory(
    id = idCategory,
    name = strCategory,
    description = strCategoryDescription,
    thumbnailUrl = strCategoryThumb
)