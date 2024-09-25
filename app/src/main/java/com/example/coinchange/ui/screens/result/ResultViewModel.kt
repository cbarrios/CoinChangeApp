package com.example.coinchange.ui.screens.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.coinchange.domain.model.CoinChange
import com.example.coinchange.domain.model.CoinChangeResult
import com.example.coinchange.domain.repository.CoinRepository
import com.example.coinchange.ui.navigation.ResultScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun onCalculateClick(coins: List<Int>, change: Int) {
        viewModelScope.launch {
            val result = calculateChange(coins, change)
            println(result)
        }
    }

    private suspend fun calculateChange(coins: List<Int>, change: Int): CoinChangeResult {
        return withContext(Dispatchers.Default) {
            if (change < 1) return@withContext CoinChangeResult.ZeroChange
            val array = MutableList(change + 1) {
                if (it == 0) emptyList() else listOf(Int.MAX_VALUE)
            }

            for (each in 1..array.lastIndex) {
                for (coin in coins) {
                    if (coin <= each && array[each - coin].firstOrNull() != Int.MAX_VALUE) {
                        val resultingCoins = array[each - coin] + coin
                        val current = array[each]
                        if (current.size == 1 && current.first() == Int.MAX_VALUE) {
                            array[each] = resultingCoins
                        } else {
                            // compare based on size
                            if (resultingCoins.size < current.size) {
                                array[each] = resultingCoins
                            }
                        }

                    }
                }
            }
            println(array)
            if (array[change].first() == Int.MAX_VALUE) return@withContext CoinChangeResult.InvalidChange

            val result = array[change].groupBy { it }.map { entry ->
                CoinChange(numberOfCoins = entry.value.size, entry.key)
            }
            return@withContext CoinChangeResult.ValidChange(result = result)
        }
    }
}