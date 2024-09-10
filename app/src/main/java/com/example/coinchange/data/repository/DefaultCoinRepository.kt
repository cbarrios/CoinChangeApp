package com.example.coinchange.data.repository

import com.example.coinchange.data.local.CoinsProvider
import com.example.coinchange.domain.model.ChangeValidation
import com.example.coinchange.domain.model.Coin
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
                isValidChange = isValidChange
            )
        }
    }
}