package com.prof18.filmatik.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesDTO(
    @SerialName("page") val page: Int,
    @SerialName("results") val movieResults: List<MovieDTO>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
)
