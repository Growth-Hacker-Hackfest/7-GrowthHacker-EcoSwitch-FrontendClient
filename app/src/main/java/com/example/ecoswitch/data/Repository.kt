package com.example.ecoswitch.data

import android.content.SharedPreferences
import io.ktor.client.HttpClient
import javax.inject.Inject

class Repository @Inject constructor(
    private val http: HttpClient,
    private val encPref: SharedPreferences
) {
}