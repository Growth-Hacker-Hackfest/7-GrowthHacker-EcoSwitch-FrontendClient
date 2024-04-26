package com.example.ecoswitch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ecoswitch.components.navbar.MyNavbar
import com.example.ecoswitch.ui.theme.EcoSwitchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

lateinit var mainViewModel: MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            mainViewModel = viewModel<MainViewModel>()
            val snackbarHostState = remember { SnackbarHostState() }

            EcoSwitchTheme {
                navController.addOnDestinationChangedListener { _, dest, _ ->
                    mainViewModel.currentRoutes.value = dest.route ?: ""

                    when (dest.route) {
                        MainNavRoutes.Dashboard.name, MainNavRoutes.Perangkat.name, MainNavRoutes.EcoAssistant.name, MainNavRoutes.Profil.name -> mainViewModel.showBottomBar.value =
                            true

                        else -> mainViewModel.showBottomBar.value = false
                    }
                }

                LaunchedEffect(key1 = mainViewModel.showSnackbar.value) {
                    if (mainViewModel.showSnackbar.value) {
                        if (mainViewModel.snackbarActionMessage.value.isNotEmpty()) {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val res = snackbarHostState.showSnackbar(
                                message = mainViewModel.snackbarMessage.value,
                                actionLabel = mainViewModel.snackbarActionMessage.value
                            )

                            when (res) {
                                SnackbarResult.Dismissed -> {
                                    mainViewModel.showSnackbar.value = false
                                }

                                SnackbarResult.ActionPerformed -> {
                                    mainViewModel.snackbarAction.value()
                                    mainViewModel.showSnackbar.value = false
                                }
                            }
                        } else {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = mainViewModel.snackbarMessage.value
                            )
                        }
                    } else {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }

                LaunchedEffect(key1 = snackbarHostState.currentSnackbarData) {
                    delay(3000)
                    mainViewModel.showSnackbar.value = false
                    mainViewModel.snackbarMessage.value = ""
                    mainViewModel.snackbarAction.value = {}
                }

                LaunchedEffect(key1 = mainViewModel.loadingWithMessage.value) {
                    if (mainViewModel.loadingWithMessage.value) {
                        snackbarHostState.showSnackbar(mainViewModel.loadingMessage.value)
                    } else {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState
                            ) {
                                Snackbar(snackbarData = it)
                            }
                        },
                        bottomBar = {
                            if (mainViewModel.showBottomBar.value) {
                                MyNavbar(
                                    onItemClick = { navController.navigate(it) },
                                    currentRoute = mainViewModel.currentRoutes.value
                                )
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .statusBarsPadding()
                                .navigationBarsPadding()
                                .padding(it)
                        ) {
                            MainNavHost(
                                startDestination = MainNavRoutes.Splash.name,
                                navController = navController
                            )
                        }

                        if (mainViewModel.loading.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}