package com.zzy.nasaapod.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.HomeImageList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit,
) {
    val getAPODsState by viewModel.getAPODsState.collectAsStateWithLifecycle()

    HomeImageList(
        modifier = modifier,
        uiState = getAPODsState,
        onLoadMore = viewModel::loadMore,
        onLikeApod =  {apod, isFav ->


        },
        onError = onError)
}