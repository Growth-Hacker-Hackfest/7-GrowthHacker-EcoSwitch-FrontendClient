package com.example.ecoswitch.model.request.pengaturan_awal

data class PengaturanAwalRequest(
    val daya: String,
    val jenis_pembayaran: String,
    val perangkat_listrik: List<SinglePengaturanAwalPerangkatRequest>
)