package com.practicum.playlistmaker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.MAIN.name
    ) {
        composable(AppScreen.MAIN.name) {
            MainScreen(
                onSearchClick = { navController.navigate(AppScreen.SEARCH.name) },
                onLibraryClick = { /* Заглушка для Медиатеки */ },
                onSettingsClick = { navController.navigate(AppScreen.SETTINGS.name) }
            )
        }
        composable(AppScreen.SEARCH.name) {
            SearchScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(AppScreen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
