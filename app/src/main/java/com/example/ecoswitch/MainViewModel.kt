package com.example.ecoswitch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ecoswitch.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val showBottomBar = mutableStateOf(false)
    val currentRoutes = mutableStateOf(MainNavRoutes.Splash.name)

    val loading = mutableStateOf(false)
    val loadingWithMessage = mutableStateOf(false)
    val loadingMessage = mutableStateOf("")
    val showSnackbar = mutableStateOf(false)
    val snackbarMessage = mutableStateOf("")
    val snackbarAction = mutableStateOf({})
    val snackbarActionMessage = mutableStateOf("")
}