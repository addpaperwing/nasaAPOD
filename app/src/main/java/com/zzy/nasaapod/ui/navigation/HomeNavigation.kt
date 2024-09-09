package com.zzy.nasaapod.ui.navigation

import android.graphics.drawable.Drawable
import android.provider.Contacts.Intents.UI
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.APODImageListInfinity

internal const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(
    viewModel: MainViewModel,
    onError: (String) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        enterTransition = null,
        exitTransition = null,
        content = {
            HomeScreen(
                viewModel = viewModel,
                onLikeChangeApod = viewModel::onLikeStateChanged,
                onError = onError
            )
        }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onLikeChangeApod: (APOD, Boolean) -> Unit,
    onError: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val apods by viewModel.apods.collectAsStateWithLifecycle()
    val newApodsState by viewModel.newApodsState.collectAsStateWithLifecycle()

    LaunchedEffect(newApodsState) {
        if (newApodsState is UiState.Error) {
            onError((newApodsState as UiState.Error).exception.message?:"Error")
        }
    }

    APODImageListInfinity(
        modifier = modifier,
        listState = listState,
        apods = apods,
        isLoading = newApodsState is UiState.Loading,
        onLoadMore = viewModel::loadMore,
        onLikeChangeApod =  onLikeChangeApod
    )
}