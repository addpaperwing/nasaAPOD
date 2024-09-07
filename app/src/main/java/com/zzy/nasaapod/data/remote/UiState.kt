package com.zzy.nasaapod.data.remote

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()

    data class Error(val exception: Throwable) : UiState<Nothing>() {

        fun getErrorMessage(): String? {
            return exception.message
        }
    }

    data class Success<out T>(val data: T) : UiState<T>()
}