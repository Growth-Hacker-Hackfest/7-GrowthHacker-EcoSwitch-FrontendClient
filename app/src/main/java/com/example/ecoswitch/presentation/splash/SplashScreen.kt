package com.example.ecoswitch.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecoswitch.R

@Composable
fun SplashScreen(
    toLogin: () -> Unit,
    toHome: () -> Unit,
    toAnalisa: () -> Unit
) {
    val viewModel = hiltViewModel<SplashViewModel>()

    LaunchedEffect(key1 = true) {
        viewModel.precheck { tokenAvailable, complete ->
            if (tokenAvailable) {
                if (complete) {
                    toHome()
                } else {
                    toAnalisa()
                }
            } else {
                toLogin()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.ic_logo_withtext,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp)
        )
    }
}