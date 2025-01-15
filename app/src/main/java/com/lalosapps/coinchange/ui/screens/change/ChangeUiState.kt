package com.lalosapps.coinchange.ui.screens.change

import com.lalosapps.coinchange.domain.model.ChangeValidation

data class ChangeUiState(
    val change: String = "",
    val validation: ChangeValidation = ChangeValidation.default
)