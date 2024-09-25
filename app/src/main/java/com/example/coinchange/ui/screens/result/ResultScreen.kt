package com.example.coinchange.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinchange.R
import com.example.coinchange.ui.screens.result.components.SimpleCoinItem

@Composable
fun ResultScreen(
    onNavigateUp: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {

    when (val uiState = viewModel.uiState.collectAsState().value) {
        ResultUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultUiState.Default -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {},
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ) {
                        Text(text = stringResource(R.string.calculate))
                    }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = stringResource(R.string.review_your_options),
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = stringResource(R.string.coins_caps),
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 32.dp)
                        ) {
                            items(uiState.coins) { value ->
                                SimpleCoinItem(value = value)
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = stringResource(R.string.change_caps),
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.your_desired_amount_is),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier.background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    CircleShape
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.value_cents_symbol,
                                        uiState.change
                                    ),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    FloatingActionButton(
                        onClick = onNavigateUp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.continue_text)
                        )
                    }
                }
            }
        }

        is ResultUiState.CalculationSuccess -> {
            // TODO
        }
    }
}