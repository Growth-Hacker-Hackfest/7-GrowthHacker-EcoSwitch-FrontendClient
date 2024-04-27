package com.example.ecoswitch.model.request.perangkat

data class CreatePerangkatConfigRequest(
    val sensitivitas: String?,
    val radius_meter: String?,
    val longitude: Double?,
    val latitude: Double?,
    val hari: List<String>?,
    val waktu_nyala: String?,
    val waktu_mati: String?,
    val sensor_id: String?
)
