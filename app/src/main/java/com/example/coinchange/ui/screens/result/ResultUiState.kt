package com.example.coinchange.ui.screens.result

import androidx.annotation.StringRes
import com.example.coinchange.domain.model.CoinChange

sealed interface ResultUiState {

    data object Loading : ResultUiState

    data class Default(
        val coins: List<Int>,
        val change: Int,
        val isCalculating: Boolean = false,
        @StringRes val calculationError: Int? = null
    ) : ResultUiState

    data class CalculationSuccess(val result: List<CoinChange>) : ResultUiState
}