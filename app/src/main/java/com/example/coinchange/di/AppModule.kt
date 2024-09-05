package com.example.coinchange.di

import com.example.coinchange.data.repository.DefaultCoinRepository
import com.example.coinchange.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinRepository(): CoinRepository = DefaultCoinRepository()
}