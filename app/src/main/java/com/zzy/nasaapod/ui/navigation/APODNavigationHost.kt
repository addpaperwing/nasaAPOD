package com.zzy.nasaapod.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zzy.nasaapod.ui.MainViewModel

@Composable
fun APODNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(viewModel, onError)

        likeScreen(viewModel)
    }
}