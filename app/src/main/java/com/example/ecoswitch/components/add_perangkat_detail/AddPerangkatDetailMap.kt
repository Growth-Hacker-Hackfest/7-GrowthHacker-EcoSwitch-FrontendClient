package com.example.ecoswitch.components.add_perangkat_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ecoswitch.components.global.BasicDropdownField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddPerangkatDetailMap(
    jarak: String,
    onJarakChanged: (String) -> Unit,
    satuan: String,
    onSatuanChanged: (String) -> Unit,
    long: Double,
    lat: Double,
    onTitikLokasiChanged: (long: Double, lat: Double) -> Unit
) {
    val satuans = listOf(
        "m",
        "Km"
    )
    val expandSatuan = remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.6f),
                value = jarak,
                onValueChange = onJarakChanged,
                label = {
                    Text(text = "Jarak")
                }
            )
            BasicDropdownField(
                modifier = Modifier.fillMaxWidth(),
                value = satuan,
                label = {
                    Text(
                        text = "Satuan",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.basicMarquee(Int.MAX_VALUE)
                    )
                },
                expanded = expandSatuan.value,
                onExpandChange = { expandSatuan.value = it }) {
                satuans.forEach {
                    item {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                onSatuanChanged(it)
                                expandSatuan.value = false
                            }
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "$long, $lat",
            onValueChange = {},
            label = {
                Text(text = "Lokasi Terkini")
            }
        )
    }
}