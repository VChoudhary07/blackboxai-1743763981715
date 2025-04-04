package com.example.wallpaperapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.ui.detail.DetailScreen
import com.example.wallpaperapp.ui.favorites.FavoritesScreen
import com.example.wallpaperapp.ui.home.HomeScreen
import com.example.wallpaperapp.ui.search.SearchScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = Destinations.HOME_ROUTE
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Home Screen
        composable(
            route = Destinations.HOME_ROUTE,
            enterTransition = { fadeInWithSlide() },
            exitTransition = { fadeOutWithSlide() }
        ) {
            HomeScreen(
                onWallpaperClick = { wallpaper ->
                    navController.navigate("${Destinations.DETAIL_ROUTE}/${wallpaper.id}")
                },
                onSearchClick = {
                    navController.navigate(Destinations.SEARCH_ROUTE)
                }
            )
        }

        // Detail Screen
        composable(
            route = "${Destinations.DETAIL_ROUTE}/{wallpaperId}",
            enterTransition = { sharedElementEnterTransition() },
            exitTransition = { sharedElementExitTransition() }
        ) { backStackEntry ->
            val wallpaperId = backStackEntry.arguments?.getString("wallpaperId") ?: ""
            DetailScreen(
                wallpaperId = wallpaperId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Favorites Screen
        composable(
            route = Destinations.FAVORITES_ROUTE,
            enterTransition = { fadeInWithSlide() },
            exitTransition = { fadeOutWithSlide() }
        ) {
            FavoritesScreen(
                onWallpaperClick = { wallpaper ->
                    navController.navigate("${Destinations.DETAIL_ROUTE}/${wallpaper.id}")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Search Screen
        composable(
            route = Destinations.SEARCH_ROUTE,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) }
        ) {
            SearchScreen(
                onWallpaperClick = { wallpaper ->
                    navController.navigate("${Destinations.DETAIL_ROUTE}/${wallpaper.id}")
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// Animation definitions
private fun AnimatedContentTransitionScope<*>.fadeInWithSlide(): EnterTransition {
    return fadeIn(animationSpec = tween(300)) + 
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
}

private fun AnimatedContentTransitionScope<*>.fadeOutWithSlide(): ExitTransition {
    return fadeOut(animationSpec = tween(300)) + 
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
}

private fun AnimatedContentTransitionScope<*>.sharedElementEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(300)) + 
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
}

private fun AnimatedContentTransitionScope<*>.sharedElementExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(300)) + 
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
}

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val FAVORITES_ROUTE = "favorites"
    const val SEARCH_ROUTE = "search"
}