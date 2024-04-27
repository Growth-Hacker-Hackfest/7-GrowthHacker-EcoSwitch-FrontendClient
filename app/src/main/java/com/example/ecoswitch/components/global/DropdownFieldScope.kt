package com.example.ecoswitch.components.global

import androidx.compose.runtime.Composable

class DropdownFieldScope {
    val contents = ArrayList<@Composable (() -> Unit)>()

    fun item(
        content: @Composable (() -> Unit)
    ) {
        contents.add(content)
    }
}