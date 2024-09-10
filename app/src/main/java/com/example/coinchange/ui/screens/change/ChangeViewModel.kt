package com.example.coinchange.ui.screens.change

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinchange.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    var uiState by mutableStateOf(ChangeUiState())
        private set

    fun onChangeEntered(change: String) {
        uiState = uiState.copy(change = change)
        validateChange(change)
    }

    private var job: Job? = null
    private fun validateChange(change: String) {
        job?.cancel()
        job = viewModelScope.launch {
            val validation = coinRepository.validateChange(change)
            uiState = uiState.copy(validation = validation)
        }
    }
}