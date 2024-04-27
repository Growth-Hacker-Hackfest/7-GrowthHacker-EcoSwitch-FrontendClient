package com.example.ecoswitch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecoswitch.presentation.add_perangkat.AddPerangkatScreen
import com.example.ecoswitch.presentation.add_perangkat_detail.AddPerangkatDetailScreen
import com.example.ecoswitch.presentation.dashboard.DashboardScreen
import com.example.ecoswitch.presentation.login.LoginScreen
import com.example.ecoswitch.presentation.analisa.AnalisaScreen
import com.example.ecoswitch.presentation.eco_assistant.EcoAssistantScreen
import com.example.ecoswitch.presentation.perangkat.PerangkatScreen
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
                },
                toAnalisa = {
                    navController.navigate(MainNavRoutes.Dashboard.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }

//                    navController.navigate(MainNavRoutes.Analisa.name) {
//                        popUpTo(navController.graph.id) {
//                            inclusive = true
//                        }
//                    }
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
            DashboardScreen()
        }

        composable(MainNavRoutes.Perangkat.name) {
            PerangkatScreen(
                onAddClick = {
                    navController.navigate(MainNavRoutes.AddPerangkat.name)
                }
            )
        }

        composable(MainNavRoutes.EcoAssistant.name) {
            EcoAssistantScreen()
        }

        composable(MainNavRoutes.Profil.name) {
            //TODO Handle this later
        }

        composable(MainNavRoutes.AddPerangkat.name) {
            AddPerangkatScreen(
                toDetail = { idPerangkat ->
                    navController.navigate("${MainNavRoutes.AddPerangkatDetail.name}/$idPerangkat")
                }
            )
        }

        composable(
            route = "${MainNavRoutes.AddPerangkatDetail.name}/{id_perangkat}",
            arguments = listOf(
                navArgument("id_perangkat") {
                    type = NavType.StringType
                }
            )
        ) {
            val idPerangkat = it.arguments?.getString("id_perangkat") ?: ""
            AddPerangkatDetailScreen(
                idPerangkat = idPerangkat,
                onBack = {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            )
        }

        composable(MainNavRoutes.Analisa.name){
            AnalisaScreen()
        }
    }
}