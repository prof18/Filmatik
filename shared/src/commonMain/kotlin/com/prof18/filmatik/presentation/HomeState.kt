package com.prof18.filmatik.presentation

data class HomeState(
    val isLoading: Boolean = true,
    val errorData: ErrorData? = null,
    val homeData: HomeData? = null,
)

data class HomeData(
    val trendingHeaderTitle: String,
    val trendingMovies: List<MovieItem>,
    val featuredHeaderTitle: String?,
    val featuredMovie: MovieItem?,
)
