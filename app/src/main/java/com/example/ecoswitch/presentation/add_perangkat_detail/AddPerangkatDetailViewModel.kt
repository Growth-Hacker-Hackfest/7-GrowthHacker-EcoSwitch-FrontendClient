package com.example.ecoswitch.presentation.add_perangkat_detail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.request.perangkat.CreatePerangkatConfigRequest
import com.example.ecoswitch.model.request.perangkat.CreatePerangkatRequest
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.perangkat.SinglePerangkatResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPerangkatDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val createDeviceState =
        MutableStateFlow<Resource<BaseResponse<Nothing>>>(Resource.NotLoadedYet())

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

    fun getAllDeviceIot() {
        viewModelScope.launch {
            repository.getAllDeviceIot().collect {
                if (it is Resource.Success) {
                    it.data?.data?.let {
                        listSensor.clear()
                        listSensor.addAll(it)
                    }
                }
            }
        }
    }

    fun createDeviceIot(
        idPerangkat: String
    ) {
        viewModelScope.launch {
            repository.createDeviceIot(
                body = CreatePerangkatRequest(
                    id = idPerangkat,
                    name = namaPerangkat.value,
                    jenis_perangkat = jenisPerangkat.value,
                    daya_listrik = dayaPerangkat.value,
                    ruangan = ruangan.value,
                    mode = mode.value,
                    config = when (mode.value) {
                        "Sensor Cahaya" -> {
                            CreatePerangkatConfigRequest(
                                //MAPS
                                latitude = null,
                                longitude = null,
                                radius_meter = null,

                                //JADWAL
                                hari = null,
                                waktu_mati = null,
                                waktu_nyala = null,

                                //CAHAYA
                                sensitivitas = sensitivitas.value,

                                //RECEIVER
                                sensor_id = null
                            )
                        }

                        "Maps" -> {
                            CreatePerangkatConfigRequest(
                                //MAPS
                                latitude = lat.value,
                                longitude = long.value,
                                radius_meter = jarak.value,

                                //JADWAL
                                hari = null,
                                waktu_mati = null,
                                waktu_nyala = null,

                                //CAHAYA
                                sensitivitas = null,

                                //RECEIVER
                                sensor_id = null
                            )
                        }

                        "Jadwal" -> {
                            CreatePerangkatConfigRequest(
                                //MAPS
                                latitude = null,
                                longitude = null,
                                radius_meter = null,

                                //JADWAL
                                hari = selectedHari,
                                waktu_mati = start.value,
                                waktu_nyala = end.value,

                                //CAHAYA
                                sensitivitas = null,

                                //RECEIVER
                                sensor_id = null
                            )
                        }

                        "Receiver" -> {
                            CreatePerangkatConfigRequest(
                                //MAPS
                                latitude = null,
                                longitude = null,
                                radius_meter = null,

                                //JADWAL
                                hari = null,
                                waktu_mati = null,
                                waktu_nyala = null,

                                //CAHAYA
                                sensitivitas = null,

                                //RECEIVER
                                sensor_id = selectedSensor.value?.id
                            )
                        }

                        else -> {
                            CreatePerangkatConfigRequest(
                                //MAPS
                                latitude = null,
                                longitude = null,
                                radius_meter = null,

                                //JADWAL
                                hari = null,
                                waktu_mati = null,
                                waktu_nyala = null,

                                //CAHAYA
                                sensitivitas = null,

                                //RECEIVER
                                sensor_id = null
                            )
                        }
                    }
                )
            ).collect {
                createDeviceState.value = it
            }
        }
    }
}