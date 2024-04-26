package com.example.ecoswitch.util

import com.example.ecoswitch.mainViewModel

object LoadingHandler {
    fun show() {
        mainViewModel.loading.value = true
    }

    fun dismiss() {
        mainViewModel.loading.value = false
    }
}