package com.zezziapp.testapp.remote

import com.zezziapp.testapp.remote.dto.CategoriesResponse
import retrofit2.http.GET

interface MealDbApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
}