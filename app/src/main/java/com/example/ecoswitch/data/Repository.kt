package com.example.ecoswitch.data

import android.content.SharedPreferences
import io.ktor.client.HttpClient
import javax.inject.Inject

class Repository @Inject constructor(
    private val http: HttpClient,
    private val encPref: SharedPreferences
) {
    fun saveToken(token: String) {
        encPref.edit().putString("token", token).apply()
    }

    fun getToken() = encPref.getString("token", "") ?: ""

    fun login(
        email: String,
        password: String
    ){
        //TODO Handle this later
    }

    fun register(
        name: String,
        email: String,
        password: String
    ){
        //TODO Handle this later
    }
}