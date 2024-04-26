package com.example.ecoswitch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecoswitch.presentation.dashboard.DashboardScreen
import com.example.ecoswitch.presentation.login.LoginScreen
import com.example.ecoswitch.presentation.splash.SplashScreen

@Composable
fun MainNavHost(
    startDestination: String,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainNavRoutes.Splash.name) {
            SplashScreen(
                toLogin = {
                    navController.navigate(MainNavRoutes.Login.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                toHome = {
                    navController.navigate(MainNavRoutes.Dashboard.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(MainNavRoutes.Login.name) {
            LoginScreen(
                toHome = {
                    navController.navigate(MainNavRoutes.Dashboard.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                toRegister = {
                    navController.navigate(MainNavRoutes.Register.name)
                }
            )
        }

        composable(MainNavRoutes.Register.name) {
            //TODO Handle this later
        }

        composable(MainNavRoutes.Dashboard.name) {
            //TODO Handle this later
        }

        composable(MainNavRoutes.Perangkat.name) {
            //TODO Handle this later
        }

        composable(MainNavRoutes.EcoAssistant.name) {
            //TODO Handle this later
        }

        composable(MainNavRoutes.Profil.name) {
            //TODO Handle this later
        }
    }
}