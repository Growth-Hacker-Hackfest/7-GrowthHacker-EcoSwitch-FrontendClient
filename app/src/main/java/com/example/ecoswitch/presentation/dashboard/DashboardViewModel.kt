package com.example.ecoswitch.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.ecoswitch.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
}