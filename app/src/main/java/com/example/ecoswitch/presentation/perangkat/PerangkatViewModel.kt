package com.example.ecoswitch.presentation.perangkat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.model.response.perangkat.SinglePerangkatResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerangkatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val devicesState =
        MutableStateFlow<Resource<BaseResponse<List<SinglePerangkatResponse>>>>(Resource.NotLoadedYet())

    fun getAllDeviceIot() {
        viewModelScope.launch {
            repository.getAllDeviceIot().collect {
                devicesState.value = it
            }
        }
    }
}