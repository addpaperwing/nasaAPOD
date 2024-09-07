package com.zzy.nasaapod.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.APODImageListInfinity

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit,
) {
    val getAPODsState by viewModel.getAPODsState.collectAsStateWithLifecycle()

    APODImageListInfinity(
        modifier = modifier,
        uiState = getAPODsState,
        onLoadMore = viewModel::loadMore,
        onLikeChangeApod =  viewModel::onLikeStateChanged,
        onError = onError)
}