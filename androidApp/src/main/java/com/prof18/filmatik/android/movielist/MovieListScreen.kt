package com.prof18.filmatik.android.movielist

import FilmatikTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prof18.filmatik.android.ui.ErrorView
import com.prof18.filmatik.android.ui.ProgressBar
import com.prof18.filmatik.presentation.ErrorData
import com.prof18.filmatik.presentation.HomeData
import com.prof18.filmatik.presentation.HomeState
import com.prof18.filmatik.presentation.MovieItem

@Composable
fun MovieListScreen(
    homeState: HomeState,
    onRetryClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        onDispose {}
    }

    Column {
        Text(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp),
            text = "Filmatik",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
        )

        when {
            homeState.isLoading -> ProgressBar()
            homeState.errorData != null -> {
                val errorData = requireNotNull(homeState.errorData)
                ErrorView(
                    errorData = errorData,
                    onRetryClick = onRetryClick,
                )
            }
            homeState.homeData != null -> {
                val homeData = requireNotNull(homeState.homeData)
                MovieView(homeData = homeData, onMovieClick)
            }
        }
    }
}

@Composable
private fun MovieView(
    homeData: HomeData,
    onMovieClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Header(homeData.trendingHeaderTitle)

        TrendingMoviesView(
            homeData = homeData,
            onMovieClick = onMovieClick
        )

        val featuredHeader = homeData.featuredHeaderTitle
        val featuredMovie = homeData.featuredMovie

        FeaturedMovieView(
            featuredHeader = featuredHeader,
            featuredMovie = featuredMovie,
            onMovieClick = onMovieClick
        )
    }
}

@Composable
private fun TrendingMoviesView(
    homeData: HomeData,
    onMovieClick: (Int) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(homeData.trendingMovies) { movieItem ->
            Card(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(200.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(
                            size = 16.dp,
                        ),
                        clip = false,
                    )
                    .clip(
                        RoundedCornerShape(
                            size = 16.dp,
                        )
                    )
                    .clickable {
                        onMovieClick(movieItem.id)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            ) {
                Column {
                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentScale = ContentScale.Crop,
                        model = movieItem.imageUrl,
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = movieItem.title,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedMovieView(
    featuredHeader: String?,
    featuredMovie: MovieItem?,
    onMovieClick: (Int) -> Unit,
) {
    if (featuredHeader != null && featuredMovie != null) {
        Header(
            modifier = Modifier
                .padding(top = 16.dp),
            title = featuredHeader,
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        size = 16.dp,
                    )
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
                    clip = false,
                )
                .clickable {
                    onMovieClick(featuredMovie.id)
                },

            ) {

            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
            )

            Box {
                AsyncImage(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                    model = featuredMovie.imageUrl,
                    contentDescription = null,
                    error = ColorPainter(MaterialTheme.colorScheme.background)
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(gradient)
                )

            }
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .align(Alignment.BottomStart),
                text = featuredMovie.title,
                color = Color.White,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun Header(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp),
        text = title,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Preview
@Composable
private fun MovieListScreenLoadingPreview() {
    val homeState = HomeState(
        isLoading = true,
        errorData = null,
        homeData = null
    )

    Surface {
        FilmatikTheme {
            MovieListScreen(
                homeState = homeState,
                onRetryClick = {},
                onMovieClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun MovieListScreenErrorPreview() {
    val homeState = HomeState(
        isLoading = false,
        errorData = ErrorData(
            message = "Sorry, something went wrong",
            buttonText = "Retry"
        ),
        homeData = null,
    )

    Surface {
        FilmatikTheme {
            MovieListScreen(
                homeState = homeState,
                onRetryClick = {},
                onMovieClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun MovieListScreenPreview() {
    val homeState = HomeState(
        isLoading = false,
        errorData = null,
        homeData = HomeData(
            trendingHeaderTitle = "Trending",
            trendingMovies = listOf(
                MovieItem(
                    id = 0,
                    title = "A movie",
                    imageUrl = "https://image.tmdb.org/t/p/original//AokFVAl1JVooW1uz2V2vxNUxfit.jpg",
                ),
                MovieItem(
                    id = 1,
                    title = "Another movie",
                    imageUrl = "",
                ), MovieItem(
                    id = 2,
                    title = "Yet Another movie",
                    imageUrl = "",
                )
            ),
            featuredHeaderTitle = "Your next movie",
            featuredMovie = MovieItem(
                id = 0,
                title = "A movie",
                imageUrl = "",
            )
        )
    )

    Surface {
        FilmatikTheme {
            MovieListScreen(
                homeState = homeState,
                onRetryClick = {},
                onMovieClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun MovieListScreenNoFeaturedPreview() {
    val homeState = HomeState(
        isLoading = false,
        errorData = null,
        homeData = HomeData(
            trendingHeaderTitle = "Trending",
            trendingMovies = listOf(
                MovieItem(
                    id = 0,
                    title = "A movie",
                    imageUrl = "",
                ),
                MovieItem(
                    id = 1,
                    title = "Another movie",
                    imageUrl = "",
                ), MovieItem(
                    id = 2,
                    title = "Yet Another movie",
                    imageUrl = "",
                )
            ),
            featuredHeaderTitle = null,
            featuredMovie = null,
        )
    )

    Surface {
        FilmatikTheme {
            MovieListScreen(
                homeState = homeState,
                onRetryClick = {},
                onMovieClick = {},
            )
        }
    }
}
