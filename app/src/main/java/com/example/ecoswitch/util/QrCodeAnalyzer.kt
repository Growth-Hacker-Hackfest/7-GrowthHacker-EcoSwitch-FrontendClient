package com.example.ecoswitch.util

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val specificArea: Rect? = null,
    private val onQrCodeScanned: (String, ImageProxy) -> Unit
) : ImageAnalysis.Analyzer {
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC,
            Barcode.FORMAT_CODE_39,
            Barcode.FORMAT_CODE_93,
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13
        )
        .enableAllPotentialBarcodes()
        .build()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val scanner = BarcodeScanning.getClient(options)
        val croppedBitmap = cropImage(
            imageProxy,
            xOffset = specificArea?.left?.toInt() ?: 0,
            yOffset = specificArea?.top?.toInt() ?: 0,
            cropHeight = specificArea?.height?.toInt() ?: imageProxy.height,
            cropWidth = specificArea?.width?.toInt() ?: imageProxy.width,
            rotationDegree = imageProxy.imageInfo.rotationDegrees
        )

        if (croppedBitmap != null) {
            val image = InputImage.fromBitmap(croppedBitmap, 0)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.forEach {
                        when (it.valueType) {
                            Barcode.TYPE_TEXT -> {
                                onQrCodeScanned(it.rawValue ?: "-", imageProxy)
                            }

                            Barcode.TYPE_URL -> {
                                onQrCodeScanned(it.url?.url ?: "-", imageProxy)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    onQrCodeScanned("", imageProxy)
                    it.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }

        } else {
            imageProxy.close()
        }
    }

    fun cropImage(
        image: ImageProxy,
        rotationDegree: Int,
        xOffset: Int,
        yOffset: Int,
        cropWidth: Int,
        cropHeight: Int
    ): Bitmap? {
        try {
            /** 1 - Convert image to Bitmap */
            var bitmap = image.toBitmap()

            /** 2 - Rotate the Bitmap */
            if (rotationDegree != 0) {
                val rotationMatrix = Matrix()
                rotationMatrix.postRotate(rotationDegree.toFloat())
                bitmap =
                    Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        rotationMatrix,
                        true
                    )
            }

            /** 3 - Crop the Bitmap */
            bitmap = Bitmap.createBitmap(bitmap, xOffset, yOffset, cropWidth, cropHeight)
            return bitmap
        } catch (e: Exception) {
            return null
        }
    }

    companion object {
        fun rotateImageProxy(
            bitmap: Bitmap,
            rotationDegree: Int
        ): Bitmap {
            if (rotationDegree != 0) {
                val rotationMatrix = Matrix()
                rotationMatrix.postRotate(rotationDegree.toFloat())
                return Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    rotationMatrix,
                    true
                )
            } else {
                return bitmap
            }
        }
    }
}