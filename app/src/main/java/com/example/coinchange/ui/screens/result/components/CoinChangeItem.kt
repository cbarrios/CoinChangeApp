package com.example.coinchange.ui.screens.result.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coinchange.R
import com.example.coinchange.domain.model.CoinChange
import com.example.coinchange.ui.theme.DarkGold
import com.example.coinchange.ui.theme.Gold

@Composable
fun CoinChangeItem(
    coinChange: CoinChange,
    modifier: Modifier = Modifier
) {

    Box {
        Box(
            modifier = modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(Gold, CircleShape)
                .border(8.dp, DarkGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.value_cents_symbol, coinChange.coinValue),
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .align(Alignment.BottomEnd),
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.onSurface
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "x${coinChange.numberOfCoins}",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}