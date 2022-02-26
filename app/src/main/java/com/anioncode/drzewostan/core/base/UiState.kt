package com.anioncode.drzewostan.core.base

sealed class UiState {
    object Idle : UiState()
    object Pending : UiState()
}