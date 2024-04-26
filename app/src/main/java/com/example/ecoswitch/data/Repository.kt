package com.example.ecoswitch.data

import android.content.SharedPreferences
import com.example.ecoswitch.model.request.auth.LoginRequest
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.util.getResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

const val BASE_URL = "https://dac8-2a09-bac1-3480-50-00-277-84.ngrok-free.app"

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
    ) = getResponse<BaseResponse<String>>(repository = this) {
        http.post("${BASE_URL}/auth/login") {
            header("Authorization", "Bearer ${getToken()}")
            setBody(LoginRequest(email, password))
            contentType(ContentType.Application.Json)
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        //TODO Handle this later
    }
}