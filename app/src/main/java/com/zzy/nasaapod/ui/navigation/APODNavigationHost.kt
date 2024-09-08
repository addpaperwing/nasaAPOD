package com.zzy.nasaapod.ui.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zzy.nasaapod.R
import com.zzy.nasaapod.ui.MainViewModel


@Composable
fun APODNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit,
) {
    val getAPODsState by viewModel.getAPODsState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(viewModel, listState, getAPODsState, onError)

        likeScreen(viewModel)
    }
}