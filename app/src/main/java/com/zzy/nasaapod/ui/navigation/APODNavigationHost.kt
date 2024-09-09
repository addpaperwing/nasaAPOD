package com.zzy.nasaapod.ui.navigation

import android.graphics.drawable.Drawable
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zzy.nasaapod.R
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.ui.MainViewModel


@Composable
fun APODNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    onError: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(viewModel, onError)

        likeScreen(viewModel)
    }
}