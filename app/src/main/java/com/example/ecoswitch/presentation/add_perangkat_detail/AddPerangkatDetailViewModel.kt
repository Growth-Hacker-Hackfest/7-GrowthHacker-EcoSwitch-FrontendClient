package com.example.ecoswitch.presentation.add_perangkat_detail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.response.perangkat.SinglePerangkatResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPerangkatDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val namaPerangkat = mutableStateOf("")
    val jenisPerangkat = mutableStateOf("")
    val expandJenisPerangkat = mutableStateOf(false)
    val dayaPerangkat = mutableStateOf("")
    val ruangan = mutableStateOf("")
    val expandRuangan = mutableStateOf(false)
    val mode = mutableStateOf("")
    val expandMode = mutableStateOf(false)

    val listSensor = mutableStateListOf<SinglePerangkatResponse>()

    val sensitivitas = mutableStateOf("")

    val jarak = mutableStateOf("")
    val satuan = mutableStateOf("")
    val long = mutableStateOf(.0)
    val lat = mutableStateOf(.0)

    val start = mutableStateOf("")
    val end = mutableStateOf("")
    val selectedHari = mutableStateListOf(
        "Senin",
        "Selasa",
        "Rabu",
        "Kamis",
        "Jumat",
        "Sabtu",
        "Minggu"
    )

    val selectedSensor = mutableStateOf<SinglePerangkatResponse?>(null)

    val showPermissionDialog = mutableStateOf(false)

    fun getAllDeviceIot(){
        viewModelScope.launch {
            repository.getAllDeviceIot().collect{
                if(it is Resource.Success){
                    it.data?.data?.let {
                        listSensor.clear()
                        listSensor.addAll(it)
                    }
                }
            }
        }
    }
}