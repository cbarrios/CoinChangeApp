package com.example.coinchange.ui.screens.result.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import com.example.coinchange.R
import com.example.coinchange.ui.theme.DarkGold
import com.example.coinchange.ui.theme.Gold

@Composable
fun SimpleCoinItem(
    value: Int,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Gold, CircleShape)
            .border(6.dp, DarkGold, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.value_cents_symbol, value),
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}