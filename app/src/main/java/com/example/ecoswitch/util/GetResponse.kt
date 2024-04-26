package com.example.ecoswitch.util

import com.example.ecoswitch.data.Repository
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

inline fun <reified T> getResponse(
    repository: Repository,
    crossinline block: suspend () -> HttpResponse
) = flow {
    emit(Resource.Loading())

    delay(600)

    try {
        val res = block()

        val body = res.body<T>()

        when (res.status) {
            HttpStatusCode.OK, HttpStatusCode.Created -> {
                emit(Resource.Success(body))
            }

            HttpStatusCode.NotFound -> {
                emit(Resource.Empty())
            }

            else -> {
                emit(Resource.Error("Telah terjadi kesalahan, coba lagi nanti!"))
            }
        }
    } catch (e: HttpRequestTimeoutException) {
        emit(Resource.Error("Timeout. Pastikan internet anda menyala atau coba lagi nanti"))
    } catch (e: Exception) {
        emit(Resource.Error("Terjadi error saat menghubungkan ke server"))
    }
}