package com.example.ecoswitch.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoswitch.data.Repository
import com.example.ecoswitch.model.response.BaseResponse
import com.example.ecoswitch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val loginState = MutableStateFlow<Resource<BaseResponse<String>>>(Resource.NotLoadedYet())

    fun login() {
        viewModelScope.launch {
            repository.login(
                email.value,
                password.value
            ).collect {
                loginState.value = it
            }
        }
    }

    fun saveToken(token: String){
        repository.saveToken(token)
    }
}