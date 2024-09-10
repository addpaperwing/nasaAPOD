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
    apods: List<APOD>,
    onItemClick: (APOD) -> Unit,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    onLikeChangeApod: (APOD, Boolean) -> Unit,
) {
    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom(1) }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            onLoadMore()
        }
    }



    APODImageList(
        modifier = modifier,
        listState = listState,
        apods = apods,
        onItemClick = onItemClick,
        onLikeChangeApod = onLikeChangeApod,
        footer = {
            if (isLoading) {
                LoadingItem()
            }
        }
    )


}

internal fun LazyListState.reachedBottom(buffer: Int = 0): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    val lastItemIndex = (lastVisibleItem?.index?:0)
    //default buffer = footer(1) + size(1) = 2
    val triggerIndex = (this.layoutInfo.totalItemsCount - 2 - buffer)
    return  lastItemIndex >= triggerIndex
}



@Preview
@Composable
fun PreviewInfinityImageList() {
    NasaAPODTheme {
        APODImageListInfinity(apods = listOf(
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
        ), isLoading = true,
            onLoadMore = {

        }, onItemClick = {}, onLikeChangeApod = { apod, b ->

        })
    }
}