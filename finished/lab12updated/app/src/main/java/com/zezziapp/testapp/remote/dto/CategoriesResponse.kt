package com.zezziapp.testapp.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(@Json(name="categories") val categories: List<CategoryDto>)