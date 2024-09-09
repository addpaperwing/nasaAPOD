package com.zzy.nasaapod.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zzy.nasaapod.R
import com.zzy.nasaapod.ui.navigation.APODNavigationHost
import com.zzy.nasaapod.ui.navigation.BottomNavigationBar
import com.zzy.nasaapod.ui.navigation.HOME_ROUTE
import com.zzy.nasaapod.ui.navigation.screens
import com.zzy.nasaapod.ui.theme.NasaAPODTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()

            NasaAPODTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = stringResource(
                                    id = R.string.app_name
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        })
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    APODNavigationHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        viewModel = viewModel,
                    )
                    { errorMessage ->
                       Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}