package com.example.coinchange.ui.screens.change

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeViewModel @Inject constructor() : ViewModel() {


    var uiState by mutableStateOf(ChangeUiState())
        private set

    fun onChangeEntered(change: String) {
        uiState = uiState.copy(change = change, isValidChange = validateChange(change))
    }

    private fun validateChange(change: String): Boolean {
        return change.toIntOrNull() in 0..99
    }

}