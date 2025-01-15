package com.lalosapps.coinchange.di

import com.lalosapps.coinchange.data.repository.DefaultCoinRepository
import com.lalosapps.coinchange.domain.repository.CoinRepository
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