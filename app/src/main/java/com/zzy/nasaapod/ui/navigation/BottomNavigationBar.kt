package com.zzy.nasaapod.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zzy.nasaapod.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        screens.values.forEachIndexed { index, destinations ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(destinations.route)
                }, icon = {
                    Icon(
                        imageVector = destinations.icon,
                        contentDescription = stringResource(id = destinations.title),
                        tint = if (selectedTabIndex == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primary.copy(0.5f)
                        }
                    )
                })
        }

    }
}

internal val screens = mapOf(
    HOME_ROUTE to Destinations.Home,
    LIKE_ROUTE to Destinations.Like,
)

sealed class Destinations(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    data object Home : Destinations(
        route = HOME_ROUTE,
        title = R.string.home,
        icon = Icons.Default.Home,
    )

    data object Like : Destinations(
        route = LIKE_ROUTE,
        title = R.string.like,
        icon = Icons.Default.Favorite
    )
}