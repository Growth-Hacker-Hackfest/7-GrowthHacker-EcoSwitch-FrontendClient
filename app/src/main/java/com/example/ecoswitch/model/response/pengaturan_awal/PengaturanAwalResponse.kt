package com.example.ecoswitch.model.response.pengaturan_awal

data class PengaturanAwalResponse(
    val response_ai: PengaturanAwalAiResponse
)

data class PengaturanAwalAiResponse(
    val total_kwh: String,
    val biaya: String,
    val co2: String
)