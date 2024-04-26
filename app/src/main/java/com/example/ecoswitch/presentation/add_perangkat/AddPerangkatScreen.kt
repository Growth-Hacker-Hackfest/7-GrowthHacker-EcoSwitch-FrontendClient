package com.example.ecoswitch.presentation.add_perangkat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecoswitch.components.global.QrCodeScannerLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddPerangkatScreen(
    toDetail: (String) -> Unit
) {
    val viewModel = hiltViewModel<AddPerangkatViewModel>()
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val vibrator = remember {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (viewModel.showShouldPermitCamera.value && !cameraPermission.status.isGranted) {
        Dialog(
            onDismissRequest = {
                viewModel.showShouldPermitCamera.value = false
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
                    Text(text = "Ijin harus dilakukan untuk menggunakan kamera. Klik \"minta ijin\", jika tidak bisa maka anda dapat ijinkan secara manual melalui setting.")
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

                        Button(
                            onClick = {
                                cameraPermission.launchPermissionRequest()
                            }
                        ) {
                            Text(text = "Minta Ijin")
                        }
                    }
                }
            }
        }
    }

    if (viewModel.isScanQr.value) {
        QrCodeScannerLayout(
            qrCode = viewModel.idPerangkat.value,
            onQrCodeScanned = { qr, _, _ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            200,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(200)
                }

                toDetail(qr)
                viewModel.isScanQr.value = false
            },
            flashState = false,
            onFlashStateChange = {
                //LET THIS EMPTY
            },
            onCancelClick = {
                //TODO Handle this later
            },
            onSelesaiClick = {
                //TODO Handle this later
            }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Tambah Perangkat") })
            },
            bottomBar = {
                BottomAppBar {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            toDetail(viewModel.idPerangkat.value)
                        },
                        enabled = viewModel.idPerangkat.value.isNotEmpty()
                    ) {
                        Text(text = "Tambah")
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    label = { Text(text = "ID Perangkat") },
                    value = viewModel.idPerangkat.value,
                    onValueChange = { viewModel.idPerangkat.value = it },
                    enabled = !viewModel.isScanQr.value
                )

                Text(text = "Atau")

                Button(
                    onClick = {
                        if (cameraPermission.status.isGranted) {
                            viewModel.isScanQr.value = true
                        } else {
                            viewModel.showShouldPermitCamera.value = true
                        }
                    },
                    enabled = !viewModel.isScanQr.value
                ) {
                    Icon(imageVector = Icons.Default.DocumentScanner, contentDescription = "")
                    Text(text = "Scan Barcode")
                }
            }
        }
    }
}