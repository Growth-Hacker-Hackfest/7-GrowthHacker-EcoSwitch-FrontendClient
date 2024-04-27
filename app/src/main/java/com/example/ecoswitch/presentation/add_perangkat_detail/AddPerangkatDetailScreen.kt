package com.example.ecoswitch.presentation.add_perangkat_detail

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailJadwal
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailMap
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailReceiver
import com.example.ecoswitch.components.add_perangkat_detail.AddPerangkatDetailSensorCahaya
import com.example.ecoswitch.components.global.BasicDropdownField
import com.example.ecoswitch.util.MyLocationService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddPerangkatDetailScreen(
    idPerangkat: String
) {
    val viewModel = hiltViewModel<AddPerangkatDetailViewModel>()
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val context = LocalContext.current
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
//        "Timer",
        "Jadwal",
        "Receiver"
    )

    LaunchedEffect(key1 = true) {
        viewModel.getAllDeviceIot()
    }

    if (viewModel.showPermissionDialog.value && !permissions.allPermissionsGranted) {
        Dialog(
            onDismissRequest = {
                viewModel.showPermissionDialog.value = false
            }
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Ijin harus dilakukan untuk menggunakan lokasi.Anda dapat ijinkan secara manual melalui setting.")
                    Row {
                        TextButton(
                            onClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context.packageName, null)
                                intent.setData(uri)
                                context.startActivity(intent)
                            }
                        ) {
                            Text(text = "Buka Setting")
                        }

                        if (permissions.shouldShowRationale) {
                            Button(
                                onClick = { permissions.launchMultiplePermissionRequest() }
                            ) {
                                Text(text = "Minta Ijin")
                            }
                        }
                    }
                }
            }
        }
    }

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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
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
                                    if (it == "Maps") {
                                        if (!permissions.allPermissionsGranted) {
                                            viewModel.showPermissionDialog.value = true
                                            return@DropdownMenuItem
                                        }
                                    }
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

                "Jadwal" -> {
                    item {
                        AddPerangkatDetailJadwal(
                            selectedHari = viewModel.selectedHari,
                            onHariChanged = { day, selected ->
                                if (selected) {
                                    if (!viewModel.selectedHari.any { it == day }) {
                                        viewModel.selectedHari.add(day)
                                    }
                                } else {
                                    viewModel.selectedHari.remove(day)
                                }
                            },
                            start = viewModel.start.value,
                            onStartChanged = {
                                viewModel.start.value = it
                            },
                            end = viewModel.end.value,
                            onEndChanged = {
                                viewModel.end.value = it
                            }
                        )
                    }
                }

                "Receiver" -> {
                    item {
                        AddPerangkatDetailReceiver(
                            listReceiver = viewModel.listSensor,
                            selected = viewModel.selectedSensor.value,
                            onSelectedChange = { id, item ->
                                viewModel.selectedSensor.value = item
                            }
                        )
                    }
                }

                else -> {}
            }
        }
    }
}