package com.zzy.nasaapod.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.home.HomeScreen
import com.zzy.nasaapod.ui.likeScreen.LikeScreen

internal const val LIKE_ROUTE = "like"

fun NavGraphBuilder.likeScreen(
    viewModel: MainViewModel,
) {
    composable(
        route = LIKE_ROUTE,
        enterTransition = null,
        exitTransition = null,
        content = {
            LikeScreen(
                viewModel = viewModel,
            )
        }
    )
}