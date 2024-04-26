package com.example.ecoswitch.presentation.add_perangkat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ecoswitch.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPerangkatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val idPerangkat = mutableStateOf("")
    val isScanQr = mutableStateOf(false)
    val showShouldPermitCamera = mutableStateOf(false)
}