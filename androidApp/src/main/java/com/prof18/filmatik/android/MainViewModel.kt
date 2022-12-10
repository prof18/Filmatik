package com.prof18.filmatik.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.filmatik.domain.DataResult
import com.prof18.filmatik.domain.MovieRepository
import com.prof18.filmatik.domain.entities.Movie
import com.prof18.filmatik.presentation.DetailState
import com.prof18.filmatik.presentation.ErrorData
import com.prof18.filmatik.presentation.HomeData
import com.prof18.filmatik.presentation.HomeState
import com.prof18.filmatik.presentation.MovieDetailItem
import com.prof18.filmatik.presentation.MovieItem
import com.prof18.filmatik.presentation.toMovieDetailItem
import com.prof18.filmatik.presentation.toMovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val _movieDetailState = MutableStateFlow(DetailState())
    val movieDetailState: StateFlow<DetailState> = _movieDetailState.asStateFlow()

    // This variable caches the retrieved movies to be able to show the movie detail when requested.
    private var movies: List<Movie> = listOf()

    init {
        getHomeState()
    }

    fun getHomeState() {
        _homeState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            when (val result = movieRepository.getPopularMovies()) {
                is DataResult.Success -> {
                    // Cache the movies
                    movies = result.data
                    _homeState.update {
                        it.copy(
                            isLoading = false,
                            homeData = generateHomeData(),
                        )
                    }
                }
                is DataResult.Error -> {
                    _homeState.update {
                        it.copy(
                            isLoading = false,
                            // TODO: localize
                            errorData = ErrorData(
                                message = "Something is not working :(",
                                buttonText = "Retry"
                            )
                        )
                    }
                }
            }
        }
    }

    fun getMovie(movieId: Int) {
        _movieDetailState.update {
            it.copy(
                isLoading = true,
            )
        }
        val movie = movies.firstOrNull { it.id == movieId }
        if (movie == null) {
            _movieDetailState.update {
                // TODO: localize
                it.copy(
                    isLoading = false,
                    errorData = ErrorData(
                        message = "This is embarrassing. I was unable to found what you have requested",
                        buttonText = "Retry"
                    )
                )
            }
        } else {
            _movieDetailState.update {
                it.copy(
                    isLoading = false,
                    movieItem = movie.toMovieDetailItem()
                )
            }
        }
    }

    private fun generateHomeData(): HomeData {
        // TODO: localize
        val trendingList = movies.map { it.toMovieItem() }

        val featuredMovie = movies.randomOrNull()?.toMovieItem()
        val featuredTitle = if (featuredMovie != null) {
            "Your next movie"
        } else {
            null
        }

        return HomeData(
            trendingHeaderTitle = "Trending",
            trendingMovies = trendingList,
            featuredHeaderTitle = featuredTitle,
            featuredMovie = featuredMovie,
        )
    }
}
