package com.lalosapps.coinchange.domain.repository

import com.lalosapps.coinchange.domain.model.ChangeValidation
import com.lalosapps.coinchange.domain.model.Coin
import com.lalosapps.coinchange.domain.model.CoinChangeResult
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoinStream(): Flow<List<Coin>>

    suspend fun toggleCoinCheck(value: Int)

    suspend fun validateChange(change: String): ChangeValidation

    suspend fun calculateCoinChange(coins: List<Int>, change: Int): CoinChangeResult
}