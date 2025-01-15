package com.lalosapps.coinchange.ui.screens.coins

import com.lalosapps.coinchange.domain.model.Coin

data class CoinsUiState(
    val coins: List<Coin>,
    val anyCoinChecked: Boolean
) {

    companion object {
        val default = CoinsUiState(coins = emptyList(), anyCoinChecked = false)
    }
}