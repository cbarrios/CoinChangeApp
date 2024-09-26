package com.example.coinchange.ui.screens.result

import com.example.coinchange.domain.model.CoinChangeResult

sealed interface ResultUiState {

    data object Loading : ResultUiState

    data class Default(
        val coins: List<Int>,
        val change: Int,
        val isCalculating: Boolean = false
    ) : ResultUiState

    data class CalculationSuccess(val result: CoinChangeResult) : ResultUiState
}