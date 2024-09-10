package com.example.coinchange.domain.model

data class ChangeValidation(
    val isInteger: Boolean,
    val isGreaterOrEqualZero: Boolean,
    val isLessThanOneHundred: Boolean,
    val isValidChange: Boolean
) {

    companion object {
        val default = ChangeValidation(
            isInteger = false,
            isGreaterOrEqualZero = false,
            isLessThanOneHundred = false,
            isValidChange = false
        )
    }
}