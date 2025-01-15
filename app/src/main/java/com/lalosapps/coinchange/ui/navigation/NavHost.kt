package com.lalosapps.coinchange.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lalosapps.coinchange.ui.screens.change.ChangeScreen
import com.lalosapps.coinchange.ui.screens.coins.CoinsScreen
import com.lalosapps.coinchange.ui.screens.result.ResultScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    onMoveTaskToBack: () -> Unit,
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
                },
                onNavigateToResultScreen = { change ->
                    navController.navigate(ResultScreen(change)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<ResultScreen> {
            ResultScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToCoinsScreen = {
                    navController.popBackStack(route = CoinsScreen, inclusive = false)
                },
                onSystemBackPress = onMoveTaskToBack
            )
        }
    }
}