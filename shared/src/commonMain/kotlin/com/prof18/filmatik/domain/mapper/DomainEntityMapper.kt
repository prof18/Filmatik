package com.prof18.filmatik.domain.mapper

import com.prof18.filmatik.data.MovieApiService
import com.prof18.filmatik.data.dto.MovieDTO
import com.prof18.filmatik.domain.entities.Movie

internal fun MovieDTO.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = "${MovieApiService.BASE_PICTURE_ADDRESS}/${this.backdropPath}",
    )
}
