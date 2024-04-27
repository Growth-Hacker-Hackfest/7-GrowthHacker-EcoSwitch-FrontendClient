package com.example.ecoswitch.model.domain

data class SinglePerangkatAnalisa(
    val index: Int,
    val jenis: String,
    val expandJenis: Boolean = false,
    val jumlah: String,
    val dayaListrik: String,
    val lamaPenggunaan: String
)
