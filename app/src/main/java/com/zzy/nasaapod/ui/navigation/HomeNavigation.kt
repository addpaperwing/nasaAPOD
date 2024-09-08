package com.zzy.nasaapod.ui.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.home.HomeScreen

internal const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    viewModel: MainViewModel,
    listState: LazyListState,
    uiState: UiState<List<APOD>>,
    onError: (Throwable) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        enterTransition = null,
        exitTransition = null,
        content = {
            HomeScreen(
                viewModel = viewModel,
                listState = listState,
                uiState = uiState,
                onError = onError
            )
        }
    )
}