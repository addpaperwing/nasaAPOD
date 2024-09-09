package com.zzy.nasaapod.ui.component

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.ui.theme.NasaAPODTheme

@SuppressLint("UnrememberedMutableState")
@Composable
fun APODImageList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    apods: List<APOD>,
    footer: @Composable () -> Unit = {},
    onItemClick: (APOD) -> Unit,
    onLikeChangeApod: (APOD, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(apods) {
            ImageItem(apod = it, onItemClick = onItemClick, onLikeTapped = onLikeChangeApod)
        }
        item {
            footer()
        }
    }
}


@Preview
@Composable
fun PreviewImageList() {
    NasaAPODTheme {
        APODImageList(apods = listOf(
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
            APOD(title = "picture title", date = "2024-01-01"),
        ), onItemClick = {}, onLikeChangeApod = { apod, b ->

        })
    }
}