package com.example.ecoswitch.model.request.perangkat

data class CreatePerangkatRequest(
    val id: String,
    val name: String,
    val jenis_perangkat: String,
    val daya_listrik: String,
    val ruangan: String,
    val mode: String,
    val config:CreatePerangkatConfigRequest
)
