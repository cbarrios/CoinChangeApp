package com.example.coinchange.ui.screens.result

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinchange.R
import com.example.coinchange.domain.model.CoinChangeResult
import com.example.coinchange.ui.screens.result.components.CoinChangeItem
import com.example.coinchange.ui.screens.result.components.SimpleCoinItem
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    onNavigateUp: () -> Unit,
    onNavigateToCoinsScreen: () -> Unit,
    onSystemBackPress: () -> Unit,
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
                        onClick = {
                            if (!uiState.isCalculating) {
                                viewModel.onCalculateClick(uiState.coins, uiState.change)
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.calculate),
                                color = if (!uiState.isCalculating) Color.Unspecified else MaterialTheme.colorScheme.onSurface
                            )
                            if (uiState.isCalculating) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape),
                                    color = MaterialTheme.colorScheme.surface,
                                    strokeWidth = 4.dp
                                )
                            }
                        }
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
                            text = stringResource(R.string.check_your_input),
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    RoundedCornerShape(16.dp)
                                )
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    text = stringResource(id = R.string.coins),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(16.dp)
                                ) {
                                    items(uiState.coins) { value ->
                                        SimpleCoinItem(value = value)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    RoundedCornerShape(16.dp)
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.change),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState()),
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
                                            modifier = Modifier.padding(
                                                horizontal = 6.dp,
                                                vertical = 3.dp
                                            ),
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
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
            BackHandler(
                enabled = true,
                onBack = onSystemBackPress
            )

            val snackbarHostState = remember { SnackbarHostState() }
            val uiScope = rememberCoroutineScope()
            val context = LocalContext.current

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                floatingActionButtonPosition = FabPosition.Center,
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            uiScope.launch {
                                val action = snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.would_you_like_to_try_again),
                                    actionLabel = context.getString(R.string.confirm)
                                )
                                if (action == SnackbarResult.ActionPerformed) {
                                    onNavigateToCoinsScreen()
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ) {
                        Text(text = stringResource(R.string.start_over))
                    }
                }
            ) { innerPadding ->

                when (val changeResult = uiState.result) {
                    CoinChangeResult.ZeroChange -> {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 32.dp),
                                text = stringResource(R.string.result),
                                style = MaterialTheme.typography.displayLarge
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
                                        RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color.Green, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(text = stringResource(R.string.zero_change_message))
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }

                    CoinChangeResult.InvalidChange -> {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 32.dp),
                                text = stringResource(R.string.result),
                                style = MaterialTheme.typography.displayLarge
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
                                        RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color.Red, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(text = stringResource(R.string.invalid_change_message))
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }

                    is CoinChangeResult.ValidChange -> {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 32.dp),
                                text = stringResource(R.string.result),
                                style = MaterialTheme.typography.displayLarge
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
                                        RoundedCornerShape(16.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color.Green, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = stringResource(id = R.string.valid_change_message)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.FixedSize(128.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(changeResult.result) { item ->
                                    CoinChangeItem(
                                        coinChange = item,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}