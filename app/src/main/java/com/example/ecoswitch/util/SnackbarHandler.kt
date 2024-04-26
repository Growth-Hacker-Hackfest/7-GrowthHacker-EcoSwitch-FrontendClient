package com.example.ecoswitch.util

import com.example.ecoswitch.mainViewModel

object SnackbarHandler {
    fun showSnackbar(message: String) {
        mainViewModel.showSnackbar.value = false
        mainViewModel.snackbarMessage.value = message
        mainViewModel.showSnackbar.value = true
    }

    fun showSnackbarWithAction(
        message: String,
        actionLabel: String,
        action: () -> Unit
    ) {
        mainViewModel.showSnackbar.value = false
        mainViewModel.snackbarMessage.value = message
        mainViewModel.snackbarActionMessage.value = actionLabel
        mainViewModel.snackbarAction.value = action
        mainViewModel.showSnackbar.value = true
    }
}