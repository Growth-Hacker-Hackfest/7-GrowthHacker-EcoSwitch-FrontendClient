package com.example.ecoswitch.components.global

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import com.example.ecoswitch.util.QrCodeAnalyzer

@Composable
fun QrCodeScannerLayout(
    qrCode: String,
    onQrCodeScanned: (String, ImageAnalysis, ImageProxy) -> Unit,
    flashState: Boolean,
    onFlashStateChange: (Boolean) -> Unit,
    onCancelClick: () -> Unit,
    onSelesaiClick: () -> Unit
) {
    val context = LocalContext.current
    val containerWidth = LocalConfiguration.current.screenWidthDp
    val containerHeight = LocalConfiguration.current.screenHeightDp
    val qrScannerAreaSize = (containerWidth * 0.7).dp
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val cameraController = remember {
        LifecycleCameraController(context)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val cameraExecutor = Executors.newSingleThreadExecutor()
                val previewView = PreviewView(ctx).also { it.controller = cameraController }

                cameraProviderFuture.addListener(
                    {
                        val cameraProvider = cameraProviderFuture.get()
                        val preview =
                            Preview.Builder()
                                .build().also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }
                        val imageAnalyzer = ImageAnalysis
                            .Builder()
                            .setTargetResolution(
                                android.util.Size(
                                    with(density) { containerWidth.dp.toPx().toInt() },
                                    with(density) { containerHeight.dp.toPx().toInt() }
                                )
                            )
                            .build()
                            .also { analyzer ->
                                analyzer.setAnalyzer(
                                    cameraExecutor,
                                    QrCodeAnalyzer(
                                        specificArea = Rect(
                                            offset = Offset(
                                                x = with(density) {
                                                    (containerWidth.dp.toPx()
                                                        .toInt() / 2) - (qrScannerAreaSize.toPx()
                                                        .toInt() / 2)
                                                }.toFloat(),
                                                y = with(density) {
                                                    (containerHeight.dp.toPx()
                                                        .toInt() / 2) - (qrScannerAreaSize.toPx()
                                                        .toInt() / 2)
                                                }.toFloat()
                                            ),
                                            size = Size(
                                                width = with(density) {
                                                    qrScannerAreaSize.toPx().toInt()
                                                }.toFloat(),
                                                height = with(density) {
                                                    qrScannerAreaSize.toPx().toInt()
                                                }.toFloat()
                                            )
                                        )
                                    ) { code, imageProxy ->
                                        onQrCodeScanned(code, analyzer, imageProxy)
                                    }
                                )
                            }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            // Unbind use cases before rebinding
                            cameraProvider.unbindAll()

                            // Bind use cases to camera
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalyzer
                            )

                        } catch (exc: Exception) {
                            Log.e("DEBUG", "Use case binding failed", exc)
                        }
                    },
                    ContextCompat.getMainExecutor(ctx)
                )

                previewView
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(Color.Black.copy(alpha = .8f))

                drawRect(
                    color = Color.Transparent,
                    topLeft = Offset(
                        x = with(density) { (containerWidth.dp.toPx() / 2) - (qrScannerAreaSize.toPx() / 2) },
                        y = with(density) { (containerHeight.dp.toPx() / 2) - (qrScannerAreaSize.toPx() / 2) }
                    ),
                    size = Size(
                        width = with(density) { qrScannerAreaSize.toPx() },
                        height = with(density) { qrScannerAreaSize.toPx() }),
                    blendMode = BlendMode.Clear
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onCancelClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = with(density) {
                                WindowInsets.navigationBars
                                    .getBottom(density)
                                    .toDp()
                            }
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Scanning...",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Text(
                            text = "Letakkan barcode pada bagian tengah layar agar lebih mudah terdeteksi",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 42.dp, bottom = 58.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (qrCode.isNotEmpty()) {
                            Button(
                                onClick = onSelesaiClick,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = ""
                                    )

                                    Text(text = "Selesai")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}