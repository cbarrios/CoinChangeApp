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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coinchange.R
import com.example.coinchange.ui.screens.change.components.ValidationItem

@Composable
fun ChangeScreen(
    onNavigateUp: () -> Unit,
    onNavigateToResultScreen: (Int) -> Unit,
    viewModel: ChangeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val validation = uiState.validation

    val requester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) {
        requester.requestFocus()
    }

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
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(R.string.enter_your_change),
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .focusRequester(requester),
                    value = uiState.change,
                    onValueChange = viewModel::onChangeEntered,
                    singleLine = true,
                    shape = RectangleShape,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.value),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(R.string.cents_symbol),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                ValidationItem(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(R.string.integer_number),
                    isValid = validation.isInteger
                )
                Spacer(modifier = Modifier.height(16.dp))
                ValidationItem(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(R.string.greater_or_equal_to_0),
                    isValid = validation.isGreaterOrEqualZero
                )
                Spacer(modifier = Modifier.height(16.dp))
                ValidationItem(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(R.string.less_than_100),
                    isValid = validation.isLessThanOneHundred
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            AnimatedVisibility(
                visible = validation.isValidChange,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                enter = slideInHorizontally(),
                exit = fadeOut()
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        // We know that actualChange is already validated if it is non null
                        validation.actualChange?.let { change ->
                            onNavigateToResultScreen(change)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ) {
                    Text(text = stringResource(R.string.continue_text))
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