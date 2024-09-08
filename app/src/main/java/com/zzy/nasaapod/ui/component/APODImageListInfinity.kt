package com.zzy.nasaapod.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.theme.NasaAPODTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun APODImageListInfinity(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    uiState: UiState<List<APOD>>,
    onLoadMore: () -> Unit,
    onLikeChangeApod: (APOD, Boolean) -> Unit,
    onError: (Throwable) -> Unit = {}
) {

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

    APODImageList(
        modifier = modifier,
        listState = listState,
        apods = apods,
        onLikeChangeApod = onLikeChangeApod,
        footer = {
            if (uiState is UiState.Loading) {
                LoadingItem()
            }
        }
    )


}

internal fun LazyListState.reachedBottom(buffer: Int = 3): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}



@Preview
@Composable
fun PreviewInfinityImageList() {
    NasaAPODTheme {
        APODImageListInfinity(uiState = UiState.Success(listOf(
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
        )), onLoadMore = {

        }, onLikeChangeApod = { apod, b ->

        })
    }
}