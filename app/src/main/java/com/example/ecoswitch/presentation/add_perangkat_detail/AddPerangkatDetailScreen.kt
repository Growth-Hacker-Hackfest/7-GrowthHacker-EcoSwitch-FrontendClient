package com.example.ecoswitch.presentation.add_perangkat_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailMap
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailSensorCahaya
import com.example.ecoswitch.components.global.BasicDropdownField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPerangkatDetailScreen(
    idPerangkat: String
) {
    val viewModel = hiltViewModel<AddPerangkatDetailViewModel>()
    val jenises = listOf(
        "AC Inverter",
        "AC Non-Inverter",
        "Kulkas Inverter",
        "Kulks Non-Inverter",
        "Televisi",
        "Lampu"
    )
    val ruangans = listOf(
        "Kamar Tidur",
        "Ruang Tamu",
        "Toilet",
        "Dapur",
        "Taman",
        "Tempat Parkir",
        "Ruang Tunggu"
    )
    val modes = listOf(
        "Sensor Cahaya",
        "Maps",
        "Timer",
        "Jadwal"
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Pengaturan Perangkat") }) },
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "")
                    Text(text = "Simpan Perangkat")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = idPerangkat,
                    onValueChange = {},
                    enabled = false,
                    label = {
                        Text(text = "ID Perangkat")
                    }
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.namaPerangkat.value,
                    onValueChange = {
                        viewModel.namaPerangkat.value = it
                    },
                    label = {
                        Text(text = "Nama Perangkat")
                    }
                )
            }

            item {
                BasicDropdownField(
                    value = viewModel.jenisPerangkat.value,
                    expanded = viewModel.expandJenisPerangkat.value,
                    onExpandChange = {
                        viewModel.expandJenisPerangkat.value = it
                    },
                    label = {
                        Text(text = "Jenis Perangkat")
                    }
                ) {
                    jenises.forEach {
                        item {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it)
                                },
                                onClick = {
                                    viewModel.jenisPerangkat.value = it
                                    viewModel.expandJenisPerangkat.value = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.dayaPerangkat.value,
                    onValueChange = {
                        viewModel.dayaPerangkat.value = it
                    },
                    trailingIcon = {
                        Text(text = "Watt")
                    },
                    label = {
                        Text(text = "Daya Listrik Perangkat")
                    }
                )
            }

            item {
                BasicDropdownField(
                    value = viewModel.ruangan.value,
                    expanded = viewModel.expandRuangan.value,
                    onExpandChange = {
                        viewModel.expandRuangan.value = it
                    },
                    label = {
                        Text(text = "Ruangan")
                    }
                ) {
                    ruangans.forEach {
                        item {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it)
                                },
                                onClick = {
                                    viewModel.ruangan.value = it
                                    viewModel.expandRuangan.value = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                BasicDropdownField(
                    value = viewModel.mode.value,
                    expanded = viewModel.expandMode.value,
                    onExpandChange = {
                        viewModel.expandMode.value = it
                    },
                    label = {
                        Text(text = "Mode")
                    }
                ) {
                    modes.forEach {
                        item {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it)
                                },
                                onClick = {
                                    viewModel.mode.value = it
                                    viewModel.expandMode.value = false
                                }
                            )
                        }
                    }
                }
            }

            when (viewModel.mode.value) {
                "Sensor Cahaya" -> {
                    item {
                        AddPerangkatDetailSensorCahaya(
                            sensitivitas = viewModel.sensitivitas.value,
                            onSensitivitasChanged = { viewModel.sensitivitas.value = it })
                    }
                }

                "Maps" -> {
                    item {
                        AddPerangkatDetailMap(
                            jarak = viewModel.jarak.value,
                            onJarakChanged = {
                                viewModel.jarak.value = it
                            },
                            satuan = viewModel.satuan.value,
                            onSatuanChanged = {
                                viewModel.satuan.value = it
                            },
                            long = viewModel.long.value,
                            lat = viewModel.lat.value,
                            onTitikLokasiChanged = { long, lat ->
                                viewModel.long.value = long
                                viewModel.lat.value = lat
                            }
                        )
                    }
                }

                "Timer" -> {}

                "Jadwal" -> {}

                else -> {}
            }
        }
    }
}