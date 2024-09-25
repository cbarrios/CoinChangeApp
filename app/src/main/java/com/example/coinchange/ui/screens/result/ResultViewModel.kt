package com.example.coinchange.ui.screens.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coinchange.domain.repository.CoinRepository
import com.example.coinchange.ui.navigation.ResultScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ResultScreen>()
    private val change = route.change

    val uiState = coinRepository.getCoinStream()
        .map { coins ->
            val selectedCoins = coins
                .filter { it.isChecked }
                .map { it.value }
            ResultUiState.Default(
                coins = selectedCoins,
                change = change
            )
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ResultUiState.Loading
        )
}