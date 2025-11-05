package com.zezziapp.testapp.features.meals.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezziapp.testapp.features.meals.domain.usecases.GetMealCategories
import com.zezziapp.testapp.features.meals.domain.usecases.SearchMealCategories
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import com.zezziapp.testapp.core.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(           // constructor DI
    private val getMeals: GetMealCategories,
    private val searchMeals: SearchMealCategories
) : ViewModel() {

    var state by mutableStateOf(MealContract.State())
        private set

    private val _effects = MutableSharedFlow<MealContract.Effect>()
    val effects = _effects.asSharedFlow()

    fun onIntent(intent: MealContract.Intent) = when (intent) {
        MealContract.Intent.Load, MealContract.Intent.Retry -> load()
        is MealContract.Intent.SearchChanged -> reduce {
            it.copy(query = intent.query, filtered = searchMeals(intent.query, it.items))
        }
        is MealContract.Intent.CategoryClicked ->
            emit(MealContract.Effect.NavigateToDetail(intent.id))
    }

    private fun load() {
        reduce { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val r = getMeals()) {
                is Result.Ok -> reduce { s ->
                    s.copy(isLoading = false, items = r.value, filtered = searchMeals(s.query, r.value))
                }
                is Result.Err -> {
                    Log.e("MealViewModel", "Error loading meals", r.throwable)
                    reduce { it.copy(isLoading = false, error = r.throwable.message ?: "Error") }
                }
            }
        }
    }

    private inline fun reduce(block: (MealContract.State) -> MealContract.State) { state = block(state) }
    private fun emit(effect: MealContract.Effect) { viewModelScope.launch { _effects.emit(effect) } }
}