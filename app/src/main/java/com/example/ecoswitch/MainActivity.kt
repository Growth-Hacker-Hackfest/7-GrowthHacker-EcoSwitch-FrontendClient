package com.example.ecoswitch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ecoswitch.ui.theme.EcoSwitchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val mainViewModel by viewModels<MainViewModel>()

            EcoSwitchTheme {

                navController.addOnDestinationChangedListener { _, dest, _ ->
                    when (dest.route) {
                        //TODO Handle this later
                        else -> mainViewModel.showBottomBar.value = false
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost(
                        startDestination = MainNavRoutes.Splash.name,
                        navController = navController
                    )
                }
            }
        }
    }
}