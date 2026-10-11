package com.mohit.machinemovieapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.machinemovieapi.presentation.screens.MovieDetailScreen
import com.mohit.machinemovieapi.presentation.screens.MovieScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.MOVIES
    ) {
        composable(Routes.MOVIES) {
            MovieScreen(
                onMovieClick = {
                    navController.navigate(Routes.movieDetail(it))
                }
            )
        }

        composable(
            route = Routes.MOVIEDETAIL
        ) { backStackEntry ->

            val movieId =
                backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0

            MovieDetailScreen(
                movieId = movieId,
            )
        }
    }
}