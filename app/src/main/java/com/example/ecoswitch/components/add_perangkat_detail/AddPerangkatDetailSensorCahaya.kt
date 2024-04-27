package com.example.ecoswitch.components.add_perangkat_detail

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.ecoswitch.components.global.BasicDropdownField

@Composable
fun AddPerangkatDetailSensorCahaya(
    sensitivitas: String,
    onSensitivitasChanged: (String) -> Unit
) {
    val list = listOf(
        "Rendah",
        "Menengah",
        "Tinggi"
    )
    val expanded = remember {
        mutableStateOf(false)
    }

    BasicDropdownField(
        value = sensitivitas,
        label = { Text(text = "Sensitivitas") },
        expanded = expanded.value,
        onExpandChange = {
            expanded.value = it
        }
    ) {
        list.forEach {
            item {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { onSensitivitasChanged(it) }
                )
            }
        }
    }
}