package com.example.coinchange.ui.screens.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinchange.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    val uiState = coinRepository.getCoinStream()
        .map { coins ->
            CoinsUiState(
                coins = coins,
                anyCoinChecked = coins.any { it.isChecked }
            )
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CoinsUiState.default
        )

    fun onCoinClick(value: Int) {
        viewModelScope.launch {
            coinRepository.toggleCoinCheck(value)
        }
    }
}