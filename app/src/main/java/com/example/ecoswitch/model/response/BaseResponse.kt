package com.example.ecoswitch.model.response

data class BaseResponse<T>(
    val message: String,
    val data: T
)
