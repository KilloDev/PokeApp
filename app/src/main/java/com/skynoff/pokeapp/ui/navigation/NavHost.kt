package com.skynoff.pokeapp.ui.navigation

import android.R.attr.name
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skynoff.pokeapp.data.local.AuthManager
import com.skynoff.pokeapp.presentation.detail.DetailScreen
import com.skynoff.pokeapp.presentation.home.HomeScreen
import com.skynoff.pokeapp.presentation.login.LoginScreen

@Composable
fun PokeAppNavigation(
    authManager: AuthManager
) {
    val navController = rememberNavController()

    val isLoggedIn by authManager.isLoggedIn.collectAsState(initial = null)
    if (isLoggedIn == null) {
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn == true) Screen.Home.route else Screen.Login.route
    ) {
        composable(Screen.Login.route){
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(route = Screen.Home.route) {
            HomeScreen(onItemClick = { name: String ->
                navController.navigate(Screen.Detail.createRoute(name))
            })
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) {
            DetailScreen()
        }
    }
}