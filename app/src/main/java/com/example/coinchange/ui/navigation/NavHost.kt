package com.example.coinchange.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinchange.ui.screens.change.ChangeScreen
import com.example.coinchange.ui.screens.coins.CoinsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CoinsScreen,
        modifier = modifier.fillMaxSize()
    ) {
        composable<CoinsScreen> {
            CoinsScreen(
                onNavigateToChangeScreen = {
                    navController.navigate(ChangeScreen) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<ChangeScreen> {
            ChangeScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}