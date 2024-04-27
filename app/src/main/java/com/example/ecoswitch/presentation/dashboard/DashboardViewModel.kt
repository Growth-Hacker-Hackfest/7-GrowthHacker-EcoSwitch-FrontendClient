package com.example.ecoswitch.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.banner.SingleBannerResponse
import com.example.ecoswitch.model.response.perangkat.SinglePerangkatResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val bannerState =
        MutableStateFlow<Resource<BaseResponse<List<SingleBannerResponse>>>>(Resource.NotLoadedYet())
    val devicesState =
        MutableStateFlow<Resource<BaseResponse<List<SinglePerangkatResponse>>>>(Resource.NotLoadedYet())

    fun getAllBanner() {
        viewModelScope.launch {
            repository.getAllBanner().collect {
                bannerState.value = it
            }
        }
    }

    fun getAllDeviceIot() {
        viewModelScope.launch {
            repository.getAllDeviceIot().collect {
                devicesState.value = it
            }
        }
    }
}