package com.example.coinchange.ui.screens.change

import com.example.coinchange.domain.model.ChangeValidation

data class ChangeUiState(
    val change: String = "",
    val validation: ChangeValidation = ChangeValidation.default
)