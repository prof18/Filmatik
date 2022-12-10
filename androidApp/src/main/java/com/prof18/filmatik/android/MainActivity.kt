package com.prof18.filmatik.android

import FilmatikTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prof18.filmatik.android.moviedetail.MovieDetailScreen
import com.prof18.filmatik.android.movielist.MovieListScreen
import com.prof18.filmatik.presentation.DetailState
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FilmatikTheme {
                val navController = rememberNavController()
                val homeState by viewModel.homeState.collectAsState()
                val detailState by viewModel.movieDetailState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Screen.MovieList.name) {
                        composable(Screen.MovieList.name) {
                            MovieListScreen(
                                homeState = homeState,
                                onRetryClick = {
                                    viewModel.getHomeState()
                                },
                                onMovieClick = {
                                    viewModel.getMovie(it)
                                    navController.navigate(Screen.MovieDetail.name)
                                }
                            )
                        }
                        composable(Screen.MovieDetail.name) {
                            MovieDetailScreen(
                                detailState = detailState,
                                onBackPressed = {
                                    navController.popBackStack()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
