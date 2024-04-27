package com.example.ecoswitch.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    fun precheck(
        onCheck:(tokenAvailable: Boolean, checked:Boolean) -> Unit
    ) {
        viewModelScope.launch {
            delay(2000)

            repository.cekPengaturanAwal().collect{
                if(it is Resource.Success){
                    it.data?.data?.let {
                        onCheck(repository.getToken().isNotEmpty(), it.is_complete)
                    }
                }
            }
        }
    }
}