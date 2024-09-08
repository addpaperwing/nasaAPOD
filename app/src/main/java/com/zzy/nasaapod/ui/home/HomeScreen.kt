package com.zzy.nasaapod.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.APODImageListInfinity

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    listState: LazyListState,
    uiState: UiState<List<APOD>>,
    onError: (Throwable) -> Unit,
) {


    APODImageListInfinity(
        modifier = modifier,
        listState = listState,
        uiState = uiState,
        onLoadMore = viewModel::loadMore,
        onLikeChangeApod =  viewModel::onLikeStateChanged,
        onError = onError)
}