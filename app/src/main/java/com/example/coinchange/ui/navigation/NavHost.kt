package com.example.coinchange.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinchange.ui.screens.coins.CoinsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CoinsScreen,
        modifier = modifier
    ) {
        composable<CoinsScreen> {
            CoinsScreen()
        }
    }
}