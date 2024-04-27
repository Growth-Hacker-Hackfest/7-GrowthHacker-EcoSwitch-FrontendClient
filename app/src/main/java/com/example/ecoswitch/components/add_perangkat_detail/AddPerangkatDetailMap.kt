package com.example.ecoswitch.components.add_perangkat_detail

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ecoswitch.components.global.BasicDropdownField
import com.example.ecoswitch.util.MyLocationService
import com.example.ecoswitch.util.MyLocationServiceState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.plugin.LocationPuck
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

@OptIn(ExperimentalFoundationApi::class, MapboxExperimental::class)
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
    val isFlied = remember {
        mutableStateOf(false)
    }

    val selfLocationPoint = remember {
        mutableStateOf(
            Point.fromLngLat(
                long, lat
            )
        )
    }
    val satuans = listOf(
        "m",
        "Km"
    )
    val context = LocalContext.current
    val myLocation = MyLocationService(context)
    val mapViewPortState = MapViewportState().apply {
        setCameraOptions {
            zoom(8.0)
            center(Point.fromLngLat(long, lat))
            pitch(0.0)
            bearing(0.0)
        }
    }

    myLocation.listen { state, _long, _lat ->
        if (state == MyLocationServiceState.ALL_GOOD) {
            if (_long != null && _lat != null) {
                if (!isFlied.value) {
                    mapViewPortState.flyTo(
                        cameraOptions {
                            zoom(8.0)
                            center(Point.fromLngLat(_long, _lat))
                            pitch(0.0)
                            bearing(0.0)
                        },
                        mapAnimationOptions {
                            duration(2000)
                        }
                    )
                    isFlied.value = true
                }

                selfLocationPoint.value = Point.fromLngLat(_long, _lat)
                onTitikLokasiChanged(_long, _lat)
            }
        }
    }

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

        MapboxMap(
            Modifier
                .fillMaxWidth()
                .height(400.dp),
            mapViewportState = mapViewPortState
        ) {
            ViewAnnotation(
                options = viewAnnotationOptions {
                    geometry(selfLocationPoint.value)
                    allowOverlap(false)
                }
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(24.dp)
                        .background(Color.Green)
                )
            }
        }
    }
}