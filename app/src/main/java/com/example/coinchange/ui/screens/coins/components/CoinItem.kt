package com.example.coinchange.ui.screens.coins.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinchange.R
import com.example.coinchange.domain.model.Coin
import com.example.coinchange.ui.theme.Gold

@Composable
fun CoinItem(
    coin: Coin,
    onCoinClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(Gold, CircleShape)
            .clickable {
                onCoinClick(coin.value)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.value_cents_symbol, coin.value),
            color = Color.Black,
            fontWeight = if (coin.isChecked) FontWeight.Bold else null,
            fontSize = if (coin.isChecked) MaterialTheme.typography.bodyLarge.fontSize else MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoinItemPreview(modifier: Modifier = Modifier) {
    CoinItem(
        Coin(1, true)
    )
}