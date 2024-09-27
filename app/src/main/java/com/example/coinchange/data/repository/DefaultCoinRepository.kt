package com.example.coinchange.data.repository

import com.example.coinchange.data.local.CoinsProvider
import com.example.coinchange.domain.model.ChangeValidation
import com.example.coinchange.domain.model.Coin
import com.example.coinchange.domain.model.CoinChange
import com.example.coinchange.domain.model.CoinChangeResult
import com.example.coinchange.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class DefaultCoinRepository : CoinRepository {

    private val coins = MutableStateFlow(CoinsProvider.coins)
    override fun getCoinStream(): Flow<List<Coin>> = coins

    override suspend fun toggleCoinCheck(value: Int) {
        withContext(Dispatchers.Default) {
            coins.update {
                it.map { coin ->
                    if (coin.value == value)
                        coin.copy(isChecked = !coin.isChecked)
                    else
                        coin
                }
            }
        }
    }

    override suspend fun validateChange(change: String): ChangeValidation {
        return withContext(Dispatchers.Default) {
            val value = change.toIntOrNull()
            val isInteger = value != null
            val isGreaterOrEqualZero = value?.let { it >= 0 } ?: false
            val isLessThanOneHundred = value?.let { it < 100 } ?: false
            val isValidChange = isInteger && isGreaterOrEqualZero && isLessThanOneHundred
            ChangeValidation(
                isInteger = isInteger,
                isGreaterOrEqualZero = isGreaterOrEqualZero,
                isLessThanOneHundred = isLessThanOneHundred,
                isValidChange = isValidChange,
                actualChange = if (isValidChange) value else null
            )
        }
    }

    override suspend fun calculateCoinChange(coins: List<Int>, change: Int): CoinChangeResult {
        return withContext(Dispatchers.Default) {
            if (change < 1) return@withContext CoinChangeResult.ZeroChange
            val array = MutableList(change + 1) {
                if (it == 0) emptyList() else listOf(Int.MAX_VALUE)
            }

            for (each in 1..array.lastIndex) {
                for (coin in coins) {
                    if (coin <= each && array[each - coin].firstOrNull() != Int.MAX_VALUE) {
                        val resultingCoins = array[each - coin] + coin
                        val current = array[each]
                        if (current.size == 1 && current.first() == Int.MAX_VALUE) {
                            array[each] = resultingCoins
                        } else {
                            // compare based on size
                            if (resultingCoins.size < current.size) {
                                array[each] = resultingCoins
                            }
                        }
                    }
                }
            }

            if (array[change].first() == Int.MAX_VALUE) return@withContext CoinChangeResult.InvalidChange

            val result = array[change].groupBy { it }.map { entry ->
                val numCoins = entry.value.size
                val coinValue = entry.key
                val amount = coinValue * numCoins
                CoinChange(
                    numberOfCoins = numCoins,
                    coinValue = coinValue,
                    changeAmount = amount
                )
            }
            return@withContext CoinChangeResult.ValidChange(
                result = result,
                totalCoins = result.sumOf { it.numberOfCoins },
                totalChange = result.sumOf { it.changeAmount }
            )
        }
    }
}