package com.example.coinchange.domain.repository

import com.example.coinchange.domain.model.ChangeValidation
import com.example.coinchange.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoinStream(): Flow<List<Coin>>

    suspend fun toggleCoinCheck(value: Int)

    suspend fun validateChange(change: String): ChangeValidation
}