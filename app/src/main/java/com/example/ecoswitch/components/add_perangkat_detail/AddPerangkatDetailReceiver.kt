package com.example.ecoswitch.components.add_perangkat_detail

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.ecoswitch.components.global.BasicDropdownField
import com.example.ecoswitch.model.response.perangkat.SinglePerangkatResponse

@Composable
fun AddPerangkatDetailReceiver(
    listReceiver: List<SinglePerangkatResponse>,
    selected: SinglePerangkatResponse?,
    onSelectedChange: (id: String, device: SinglePerangkatResponse) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    BasicDropdownField(
        value = selected?.name ?: "",
        label = { Text(text = "Sensor") },
        expanded = expanded.value,
        onExpandChange = {
            expanded.value = it
        }
    ) {
        listReceiver.forEach {
            item {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        onSelectedChange(it.id, it)
                        expanded.value = false
                    }
                )
            }
        }
    }
}