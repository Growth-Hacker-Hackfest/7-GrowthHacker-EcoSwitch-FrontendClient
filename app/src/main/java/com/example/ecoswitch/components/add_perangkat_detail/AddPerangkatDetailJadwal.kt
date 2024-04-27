package com.example.ecoswitch.components.add_perangkat_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddPerangkatDetailJadwal(
    selectedHari: List<String>,
    onHariChanged: (String, Boolean) -> Unit,
    start: String,
    onStartChanged: (String) -> Unit,
    end: String,
    onEndChanged: (String) -> Unit
) {
    val days = listOf(
        "Senin",
        "Selasa",
        "Rabu",
        "Kamis",
        "Jumat",
        "Sabtu",
        "Minggu"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        days.forEach { day ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Checkbox(
                    checked = selectedHari.any { it == day },
                    onCheckedChange = {
                        onHariChanged(day, it)
                    }
                )

                Text(text = day)
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = start,
            onValueChange = onStartChanged,
            label = {
                Text(text = "Start (DD-MM-YYYY)")
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = end,
            onValueChange = onEndChanged,
            label = {
                Text(text = "End (DD-MM-YYYY)")
            }
        )
    }
}