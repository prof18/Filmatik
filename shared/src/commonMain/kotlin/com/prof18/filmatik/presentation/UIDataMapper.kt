package com.prof18.filmatik.presentation

import com.prof18.filmatik.domain.entities.Movie

fun Movie.toMovieItem(): MovieItem =
    MovieItem(
        id = id,
        title = title,
        imageUrl = posterPath,
    )

fun Movie.toMovieDetailItem(): MovieDetailItem =
    MovieDetailItem(
        id = id,
        title = title,
        imageUrl = posterPath,
        content = overview,
    )