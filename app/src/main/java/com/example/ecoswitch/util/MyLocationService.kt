package com.example.ecoswitch.util

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MyLocationService(
    private val context: Context
) {
    private var locationClient: FusedLocationProviderClient? = null
    private var locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var locationCallback: LocationCallback? = null
    private var locationRequest = LocationRequest
        .create()
        .setInterval(3000)
        .setFastestInterval(2000)

    private fun create() {
        locationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun listen(
        callback: (
            state: MyLocationServiceState,
            long: Double?,
            lat: Double?
        ) -> Unit
    ) {
        if (locationClient == null) create()
        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.locations.lastOrNull()?.let {
                        callback(
                            MyLocationServiceState.ALL_GOOD,
                            it.longitude,
                            it.latitude
                        )
                    }
                }
            }
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            callback(MyLocationServiceState.GPS_OFF, null, null)
            return
        }
        if (
            context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            callback(MyLocationServiceState.PERMISSION_UNGRANTED, null, null)
            return
        }

        locationClient?.lastLocation?.addOnSuccessListener {
            callback(
                MyLocationServiceState.ALL_GOOD,
                it.longitude,
                it.latitude
            )
        }

        Log.e("MASUK", "KESINI")

        locationCallback?.let { locationCallbackNotNull ->
            locationClient?.requestLocationUpdates(
                locationRequest,
                locationCallbackNotNull,
                Looper.getMainLooper()
            )
        }
    }

    fun unlisten() {
        locationCallback?.let { locationCallbackNotNull ->
            locationClient?.removeLocationUpdates(locationCallbackNotNull)
            Log.e("LOG", "CALLED")
        }
    }
}