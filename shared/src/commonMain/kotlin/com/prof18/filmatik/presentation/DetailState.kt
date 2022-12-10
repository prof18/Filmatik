package com.prof18.filmatik.presentation

data class DetailState(
    val isLoading: Boolean = true,
    val errorData: ErrorData? = null,
    val movieItem: MovieDetailItem? = null,
)

data class MovieDetailItem(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val content: String,
)
