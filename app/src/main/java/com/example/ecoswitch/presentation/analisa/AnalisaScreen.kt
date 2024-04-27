package com.example.ecoswitch.presentation.analisa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecoswitch.components.global.BasicDropdownField
import com.example.ecoswitch.model.domain.SinglePerangkatAnalisa

@Composable
fun AnalisaScreen() {
    val viewModel = hiltViewModel<AnalisaViewModel>()
    val golonganListrik = listOf(
        "450VA",
        "900VA",
        "1300VA",
        "2200VA",
        "3500 - 5500VA",
        "> 6600VA"
    )
    val jenisPembayaran = listOf(
        "Prabayar",
        "Pascabayar"
    )
    val jenises = listOf(
        "AC Inverter",
        "AC Non-Inverter",
        "Kulkas Inverter",
        "Kulks Non-Inverter",
        "Televisi",
        "Lampu"
    )
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Simpan dan Analisa")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Analisa Penggunaan Listrik",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Lengkapi data di bawah untuk menganalisis penggunaan listrikmu saat ini",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                BasicDropdownField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.golonganListrik.value,
                    expanded = viewModel.expandGolonganListrik.value,
                    onExpandChange = {
                        viewModel.expandGolonganListrik.value = it
                    },
                    label = {
                        Text(text = "Golongan Daya Listrik")
                    }
                ) {
                    golonganListrik.forEach {
                        item {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    viewModel.golonganListrik.value = it
                                    viewModel.expandGolonganListrik.value = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                BasicDropdownField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.jenisPembayaran.value,
                    expanded = viewModel.expandJenisPembayaran.value,
                    onExpandChange = {
                        viewModel.expandJenisPembayaran.value = it
                    },
                    label = {
                        Text(text = "Jenis Pembayaran")
                    }
                ) {
                    jenisPembayaran.forEach {
                        item {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    viewModel.jenisPembayaran.value = it
                                    viewModel.expandJenisPembayaran.value = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Penggunaan Perangkat Listrik",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            itemsIndexed(viewModel.perangkat) { index, item ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Perangkat ${index + 1}")
                        Text(
                            modifier = Modifier.clickable {
                                viewModel.perangkat.removeAt(index)
                            },
                            text = "Hapus",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    BasicDropdownField(
                        value = item.jenis,
                        expanded = item.expandJenis,
                        onExpandChange = { expand ->
                            viewModel.perangkat.replaceAll { oldItem ->
                                if (oldItem.index == index) {
                                    oldItem.copy(expandJenis = expand)
                                } else {
                                    oldItem
                                }
                            }
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
                                        viewModel.perangkat.replaceAll { oldItem ->
                                            if (oldItem.index == index) {
                                                oldItem.copy(
                                                    jenis = it,
                                                    expandJenis = false
                                                )
                                            } else {
                                                oldItem.copy(
                                                    expandJenis = false
                                                )
                                            }
                                        }

                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        value = item.jumlah,
                        onValueChange = {
                            viewModel.perangkat.replaceAll { oldItem ->
                                if (oldItem.index == index) {
                                    oldItem.copy(jumlah = it)
                                } else {
                                    oldItem
                                }
                            }
                        },
                        label = {
                            Text(text = "Jumlah Perangkat")
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = item.dayaListrik,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            viewModel.perangkat.replaceAll { oldItem ->
                                if (oldItem.index == index) {
                                    oldItem.copy(dayaListrik = it)
                                } else {
                                    oldItem
                                }
                            }
                        },
                        label = {
                            Text(text = "Daya Listrik Perangkat")
                        },
                        trailingIcon = {
                            Text(text = "Watt")
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = item.lamaPenggunaan,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            viewModel.perangkat.replaceAll { oldItem ->
                                if (oldItem.index == index) {
                                    oldItem.copy(lamaPenggunaan = it)
                                } else {
                                    oldItem
                                }
                            }
                        },
                        label = {
                            Text(text = "Lama Penggunaan Sehari")
                        },
                        trailingIcon = {
                            Text(text = "Jam")
                        }
                    )
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.perangkat.add(
                            SinglePerangkatAnalisa(
                                viewModel.perangkat.size,
                                "",
                                false,
                                "",
                                "",
                                ""
                            )
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")

                    Text(text = "Tambah Perangkat Listrik")
                }
            }
        }
    }
}