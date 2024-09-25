package com.example.coinchange.domain.model

data class CoinChange(
    val numberOfCoins: Int,
    val coinValue: Int
)

sealed interface CoinChangeResult {

    data object ZeroChange : CoinChangeResult

    data object InvalidChange : CoinChangeResult

    data class ValidChange(val result: List<CoinChange>) : CoinChangeResult
}