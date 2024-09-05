package com.example.coinchange.data.repository

import com.example.coinchange.data.local.CoinsProvider
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
}