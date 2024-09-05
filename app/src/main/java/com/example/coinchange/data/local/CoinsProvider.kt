package com.example.coinchange.data.local

import com.example.coinchange.domain.model.Coin

object CoinsProvider {

    val coins = listOf(
        Coin(value = 1, isChecked = true),
        Coin(value = 5, isChecked = true),
        Coin(value = 10, isChecked = true),
        Coin(value = 25, isChecked = true)
    )
}