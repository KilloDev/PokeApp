package com.skynoff.pokeapp.ui.navigation

import android.R.attr.name
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skynoff.pokeapp.presentation.detail.DetailScreen
import com.skynoff.pokeapp.presentation.home.HomeScreen

@Composable
fun PokeAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
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