package com.example.coinchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.coinchange.ui.navigation.AppNavHost
import com.example.coinchange.ui.theme.CoinChangeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinChangeTheme {
                AppNavHost(
                    onMoveTaskToBack = {
                        moveTaskToBack(false)
                    }
                )
            }
        }
    }
}