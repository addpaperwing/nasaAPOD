package com.zzy.nasaapod.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.home.HomeScreen

internal const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        enterTransition = null,
        exitTransition = null,
        content = {
            HomeScreen(
                viewModel = viewModel,
                onError = onError
            )
        }
    )
}