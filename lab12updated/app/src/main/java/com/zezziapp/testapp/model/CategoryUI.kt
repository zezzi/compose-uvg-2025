package com.zezziapp.testapp.model

data class CategoryUI(
    val id: String,
    val name: String,
    val thumbUrl: String
)

data class MealUI(
    val id: String,
    val categoryId: String,
    val name: String,
    val thumbUrl: String
)