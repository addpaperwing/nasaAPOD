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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zzy.nasaapod.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?: HOME_ROUTE

    NavigationBar {
        screens.values.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.title),
                        tint = if (currentRoute == screen.route) {
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
    HOME_ROUTE to Screen.Home,
    LIKE_ROUTE to Screen.Like,
)

sealed class Screen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    data object Home : Screen(
        route = HOME_ROUTE,
        title = R.string.home,
        icon = Icons.Default.Home,
    )

    data object Like : Screen(
        route = LIKE_ROUTE,
        title = R.string.like,
        icon = Icons.Default.Favorite
    )
}