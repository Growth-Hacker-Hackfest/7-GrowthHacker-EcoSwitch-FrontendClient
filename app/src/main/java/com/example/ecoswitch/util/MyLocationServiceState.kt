package com.example.ecoswitch.util

import android.content.Context
import android.location.LocationManager
import javax.inject.Inject

enum class MyLocationServiceState {
    GPS_OFF,
    PERMISSION_UNGRANTED,
    ALL_GOOD
}