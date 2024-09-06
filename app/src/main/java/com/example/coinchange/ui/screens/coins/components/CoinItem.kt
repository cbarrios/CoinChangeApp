package com.example.coinchange.ui.screens.coins.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinchange.R
import com.example.coinchange.domain.model.Coin
import com.example.coinchange.ui.theme.DarkGold
import com.example.coinchange.ui.theme.Gold

@Composable
fun CoinItem(
    coin: Coin,
    modifier: Modifier = Modifier,
    onCoinClick: (Int) -> Unit = {}
) {
    Box {
        Box(
            modifier = modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(Gold, CircleShape)
                .clickable { onCoinClick(coin.value) }
                .border(8.dp, DarkGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.value_cents_symbol, coin.value),
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
            AnimatedVisibility(coin.isChecked) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.onSurface
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoinItemPreview(modifier: Modifier = Modifier) {
    CoinItem(
        Coin(1, true)
    )
}