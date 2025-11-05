package com.zezziapp.testapp.features.meals.ui

import com.zezziapp.testapp.remote.domain.MealCategory

object MealContract {

    sealed interface Intent {
        data object Load : Intent
        data object Retry : Intent
        data class SearchChanged(val query: String) : Intent
        data class CategoryClicked(val id: String) : Intent
    }

    data class State(
        val isLoading: Boolean = false,
        val query: String = "",
        val items: List<MealCategory> = emptyList(),
        val filtered: List<MealCategory> = emptyList(),
        val error: String? = null
    )

    sealed interface Effect {
        data class NavigateToDetail(val id: String) : Effect
        data class ShowMessage(val text: String) : Effect
    }
}