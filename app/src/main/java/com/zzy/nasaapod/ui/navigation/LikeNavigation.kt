package com.zzy.nasaapod.ui.navigation

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.MainViewModel
import com.zzy.nasaapod.ui.component.APODImageList

internal const val LIKE_ROUTE = "like"

fun NavGraphBuilder.likeScreen(
    viewModel: MainViewModel,
    onItemClick: (APOD) -> Unit,
) {
    composable(
        route = LIKE_ROUTE,
        enterTransition = null,
        exitTransition = null,
        content = {
            LikeScreen(
                viewModel = viewModel,
                onItemClick = onItemClick,
                onLikeChangeApod = viewModel::onLikeStateChanged
            )
        }
    )
}

@Composable
fun LikeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onItemClick: (APOD) -> Unit,
    onLikeChangeApod: (APOD, Boolean) -> Unit,
) {
    val apods by viewModel.savedAPODs.collectAsStateWithLifecycle()

    APODImageList(
        modifier = modifier,
        apods = apods,
        onItemClick = onItemClick,
        onLikeChangeApod = onLikeChangeApod
    )
}