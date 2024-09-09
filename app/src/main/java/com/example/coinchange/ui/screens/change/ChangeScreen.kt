package com.example.coinchange.ui.screens.change

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinchange.R

@Composable
fun ChangeScreen(
    onNavigateUp: () -> Unit,
    viewModel: ChangeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.enter_your_change),
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.change,
                    onValueChange = viewModel::onChangeEntered,
                    singleLine = true,
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            }
            AnimatedVisibility(
                visible = uiState.isValidChange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                enter = slideInHorizontally(),
                exit = fadeOut()
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (uiState.isValidChange) {
                            // TODO
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ) {
                    Text(text = stringResource(R.string.go))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.edit_coins)
                    )
                }
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