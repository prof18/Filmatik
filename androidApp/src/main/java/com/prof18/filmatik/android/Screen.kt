package com.prof18.filmatik.android

sealed class Screen(val name: String) {
    object MovieList: Screen("movie_list")
    object MovieDetail: Screen("movie_detail")
}
