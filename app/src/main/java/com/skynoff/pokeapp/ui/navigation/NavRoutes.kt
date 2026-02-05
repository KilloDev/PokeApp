package com.skynoff.pokeapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{pokemonName}") {
        fun createRoute(name: String) = "detail_screen/$name"
    }
}