package com.prof18.filmatik.domain

import com.prof18.filmatik.data.MovieApiService
import com.prof18.filmatik.domain.entities.Movie
import com.prof18.filmatik.domain.mapper.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieApiService: MovieApiService,
) {

    suspend fun getPopularMovies(): DataResult<List<Movie>> {
        val popularMovieResult = movieApiService.getPopularMovies()
        if (popularMovieResult is DataResult.Error) {
            return DataResult.Error(popularMovieResult.throwable)
        }
        return DataResult.Success(
            (popularMovieResult as DataResult.Success).data
                .movieResults
                .map { movieDTO ->
                    movieDTO.toMovie()
                },
        )
    }
}
