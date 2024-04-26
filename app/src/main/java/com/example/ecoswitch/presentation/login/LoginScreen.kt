package com.example.ecoswitch.presentation.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ecoswitch.R
import com.example.ecoswitch.util.LoadingHandler
import com.example.ecoswitch.util.Resource
import com.example.ecoswitch.util.SnackbarHandler
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    toHome: () -> Unit,
    toRegister: () -> Unit
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val loginState = viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = loginState.value) {
        when (loginState.value) {
            is Resource.Loading -> {
                LoadingHandler.show()
            }

            is Resource.Success -> {
                loginState.value.data?.data?.let { token ->
                    viewModel.saveToken(token)
                }

                LoadingHandler.dismiss()
                SnackbarHandler.showSnackbar("Berhasil Login")
                delay(1500)
                toHome()
            }

            is Resource.Error -> {
                loginState.value.message?.let {msg ->
                    SnackbarHandler.showSnackbar("ERROR: $msg")
                }
                LoadingHandler.dismiss()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AsyncImage(
            model = R.drawable.ic_logo_withtext,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .padding(top = 64.dp, bottom = 160.dp)
        )

        Column {
            Text("Masuk", style = MaterialTheme.typography.titleLarge)
            Text("Selamat datang di EcoSwitch!", style = MaterialTheme.typography.bodyLarge)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            onClick = {
                if (viewModel.email.value.isEmpty() || viewModel.password.value.isEmpty()) {
                    SnackbarHandler.showSnackbar("Pastikan data diisi dengan benar")
                    return@Button
                }

                viewModel.login()
            }
        ) {
            Text(text = "Masuk")
        }
    }
}