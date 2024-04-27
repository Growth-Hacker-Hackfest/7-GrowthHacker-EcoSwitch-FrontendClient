package com.example.ecoswitch.components.global

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun BasicDropdownField(
    modifier: Modifier = Modifier,
    value: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    label: @Composable (() -> Unit) = {},
    placeholder: @Composable (() -> Unit) = {},
    content: DropdownFieldScope.() -> Unit
) {
    val scope = DropdownFieldScope()
    content(scope)

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {},
            label = label,
            placeholder = placeholder,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onExpandChange(!expanded)
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            },
            readOnly = true
        )

        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .shadow(elevation = 2.dp),
            expanded = expanded,
            onDismissRequest = {
                onExpandChange(false)
            }
        ) {
            scope.contents.forEach {
                it()
            }
        }
    }
}