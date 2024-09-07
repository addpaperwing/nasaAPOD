package com.zzy.nasaapod.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.theme.NasaAPODTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeImageList(
    modifier: Modifier = Modifier,
    uiState: UiState<List<APOD>>,
    onLoadMore: () -> Unit,
    onLikeApod: (APOD, Boolean) -> Unit,
    onError: (Throwable) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val apods: MutableList<APOD> = remember { mutableStateListOf() }

    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom() }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onLoadMore()
    }
//
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            apods += uiState.data
        } else if (uiState is UiState.Error) {
            onError(uiState.exception)
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(apods) {
            ImageItem(apod = it, onLikeTapped = onLikeApod)
        }
        if (uiState is UiState.Loading) {
            item {
                LoadingItem()
            }
        }
    }
}

internal fun LazyListState.reachedBottom(buffer: Int = 3): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}



@Preview
@Composable
fun PreviewImageList() {
    NasaAPODTheme {
        HomeImageList(uiState = UiState.Success(listOf(
            APOD(title = "picture title", date = "2024-01-01"),
//            APOD(title = "picture title", date = "2024-01-01"),
//            APOD(title = "picture title", date = "2024-01-01"),
//            APOD(title = "picture title", date = "2024-01-01"),
//            APOD(title = "picture title", date = "2024-01-01"),
        )), onLoadMore = {

        }, onLikeApod = { apod, b ->

        })
    }
}