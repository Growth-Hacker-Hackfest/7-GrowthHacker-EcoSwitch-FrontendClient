package com.example.ecoswitch.presentation.add_perangkat_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPerangkatDetailScreen(
    idPerangkat: String
) {
    val viewModel = hiltViewModel<AddPerangkatDetailViewModel>()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Pengaturan Perangkat") })
        },
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
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.jenisPerangkat.value,
                    onValueChange = {
                        viewModel.jenisPerangkat.value = it
                    },
                    label = {
                        Text(text = "Jenis Perangkat")
                    }
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.dayaPerangkat.value,
                    onValueChange = {
                        viewModel.dayaPerangkat.value = it
                    },
                    label = {
                        Text(text = "Daya Listrik Perangkat")
                    }
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.ruangan.value,
                    onValueChange = {
                        viewModel.ruangan.value = it
                    },
                    label = {
                        Text(text = "Ruangan")
                    }
                )
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.mode.value,
                    onValueChange = {
                        viewModel.mode.value = it
                    },
                    label = {
                        Text(text = "Mode")
                    }
                )
            }
        }
    }
}