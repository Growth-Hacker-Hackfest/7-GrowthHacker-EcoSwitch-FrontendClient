package com.example.ecoswitch.presentation.analisa

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.domain.SinglePerangkatAnalisa
import com.example.ecoswitch.model.request.pengaturan_awal.PengaturanAwalRequest
import com.example.ecoswitch.model.request.pengaturan_awal.SinglePengaturanAwalPerangkatRequest
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.pengaturan_awal.PengaturanAwalResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
    val analisaState =
        MutableStateFlow<Resource<BaseResponse<PengaturanAwalResponse>>>(Resource.NotLoadedYet())

    fun sendRequest() {
        viewModelScope.launch {
            repository.sendPengaturanAwal(
                body = PengaturanAwalRequest(
                    daya = golonganListrik.value.removeSuffix("VA"),
                    jenis_pembayaran = jenisPembayaran.value,
                    perangkat_listrik = perangkat.map {
                        SinglePengaturanAwalPerangkatRequest(
                            jenis_perangkat = it.jenis,
                            jumlah = it.jumlah.toIntOrNull() ?: 0,
                            daya_listrik = it.dayaListrik.toIntOrNull() ?: 0,
                            lama_pemakaian = it.lamaPenggunaan.toIntOrNull() ?: 0
                        )
                    }
                )
            ).collect {
                analisaState.value = it
            }
        }
    }
}