package com.example.coinchange.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object CoinsScreen

@Serializable
object ChangeScreen

@Serializable
data class ResultScreen(val change: Int)