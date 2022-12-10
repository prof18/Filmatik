package com.prof18.filmatik.android.moviedetail

import FilmatikTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prof18.filmatik.android.ui.ErrorView
import com.prof18.filmatik.android.ui.ProgressBar
import com.prof18.filmatik.presentation.DetailState
import com.prof18.filmatik.presentation.ErrorData
import com.prof18.filmatik.presentation.MovieDetailItem

@Composable
fun MovieDetailScreen(
    detailState: DetailState,
    onBackPressed: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        onDispose {}
    }

    when {
        detailState.isLoading -> ProgressBar()
        detailState.errorData != null -> {
            val errorData = requireNotNull(detailState.errorData)
            ErrorView(
                errorData = errorData,
            )
        }
        detailState.movieItem != null -> {
            val movieItem = requireNotNull(detailState.movieItem)
            MovieDetailView(
                movieItem = movieItem,
                onBackPressed = onBackPressed,
            )
        }
    }

}

@Composable
fun MovieDetailView(
    movieItem: MovieDetailItem,
    onBackPressed: () -> Unit,
) {

    Column {
        AsyncImage(
            modifier = Modifier
                .height(250.dp),
            contentScale = ContentScale.Crop,
            model = movieItem.imageUrl,
            contentDescription = null,
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = movieItem.title,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = movieItem.content,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun MovieDetailLoadingPreview() {
    val movieDetailState = DetailState(
        isLoading = true,
        errorData = null,
        movieItem = null,
    )

    Surface {
        FilmatikTheme {
            MovieDetailScreen(
                detailState = movieDetailState,
                onBackPressed = {},
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailErrorPreview() {
    val movieDetailState = DetailState(
        isLoading = false,
        errorData = ErrorData(
            message = "Sorry, something went wrong",
            buttonText = "Retry"
        ),
        movieItem = null,
    )

    Surface {
        FilmatikTheme {
            MovieDetailScreen(
                detailState = movieDetailState,
                onBackPressed = {},
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailMoviePreview() {
    val movieDetailState = DetailState(
        isLoading = false,
        errorData = null,
        movieItem = MovieDetailItem(
            id = 0,
            title = "A movie",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
            imageUrl = "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg",
        ),
    )

    Surface {
        FilmatikTheme {
            MovieDetailScreen(
                detailState = movieDetailState,
                onBackPressed = {},
            )
        }
    }
}
