package com.zzy.nasaapod.ui.likeScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.APODImageList

@Composable
fun LikeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val apods by viewModel.savedAPODs.collectAsStateWithLifecycle()

    APODImageList(
        modifier = modifier,
        apods = apods,
        onLikeChangeApod = viewModel::onLikeStateChanged
    )
}