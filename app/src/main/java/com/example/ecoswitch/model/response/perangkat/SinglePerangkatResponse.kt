package com.example.ecoswitch.model.response.perangkat

data class SinglePerangkatResponse(
    val id: String,
    val name: String,
    val user_id: String,
    val is_on: Boolean?,
    val jenis_perangkat: String,
    val daya_listrik: Long,
    val ruangan: String,
    val mode: String,
//    val config: SinglePerangkatConfigResponse
)
