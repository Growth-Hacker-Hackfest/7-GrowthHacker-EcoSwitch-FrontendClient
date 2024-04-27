package com.example.ecoswitch.data

import android.content.SharedPreferences
import com.example.ecoswitch.model.request.auth.LoginRequest
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.banner.SingleBannerResponse
import com.example.ecoswitch.util.getResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

const val BASE_URL = "http://199.180.130.189"

class Repository @Inject constructor(
    private val http: HttpClient,
    private val encPref: SharedPreferences
) {
    fun saveToken(token: String) {
        encPref.edit().putString("token", token).apply()
    }

    fun getToken() = encPref.getString("token", "") ?: ""

    fun getAllBanner() = getResponse<BaseResponse<List<SingleBannerResponse>>>(repository = this){
        http.get("${BASE_URL}/banner") {
            header("Authorization", "Bearer ${getToken()}")
        }
    }

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