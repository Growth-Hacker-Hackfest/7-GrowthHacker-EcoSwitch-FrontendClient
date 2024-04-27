package com.example.ecoswitch.presentation.analisa

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.domain.SinglePerangkatAnalisa
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalisaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val golonganListrik = mutableStateOf("")
    val expandGolonganListrik = mutableStateOf(false)
    val jenisPembayaran = mutableStateOf("")
    val expandJenisPembayaran = mutableStateOf(false)

    val perangkat = mutableStateListOf<SinglePerangkatAnalisa>()
}