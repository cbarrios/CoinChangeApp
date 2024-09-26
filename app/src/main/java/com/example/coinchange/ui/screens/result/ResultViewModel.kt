package com.example.coinchange.ui.screens.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coinchange.domain.model.CoinChangeResult
import com.example.coinchange.domain.repository.CoinRepository
import com.example.coinchange.ui.navigation.ResultScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ResultScreen>()
    private val change = route.change

    private val selectedCoins = coinRepository.getCoinStream()
        .map { coins ->
            coins
                .filter { it.isChecked }
                .map { it.value }
        }
        .flowOn(Dispatchers.Default)

    private val isCalculating = MutableStateFlow(false)
    private val coinChangeResult = MutableStateFlow<CoinChangeResult?>(null)

    val uiState = combine(
        selectedCoins,
        isCalculating,
        coinChangeResult
    ) { coins, isCalculating, result ->
        if (result != null) {
            ResultUiState.CalculationSuccess(result = result)
        } else {
            ResultUiState.Default(
                coins = coins,
                change = change,
                isCalculating = isCalculating
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ResultUiState.Loading
    )

    fun onCalculateClick(coins: List<Int>, change: Int) {
        viewModelScope.launch {
            isCalculating.value = true
            coinChangeResult.value = coinRepository.calculateCoinChange(coins, change)
            isCalculating.value = false
        }
    }
}