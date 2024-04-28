package com.example.ecoswitch.model.request.pengaturan_awal

data class SinglePengaturanAwalPerangkatRequest(
    val jenis_perangkat: String,
    val jumlah: Int,
    val daya_listrik: Int,
    val lama_pemakaian: Int
)
