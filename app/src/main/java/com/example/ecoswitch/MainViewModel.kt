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
}