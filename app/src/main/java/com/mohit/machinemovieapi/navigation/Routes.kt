package com.mohit.machinemovieapi.navigation

object Routes {
    const val MOVIES = "movies"
    const val MOVIEDETAIL = "movie_detail/{movieId}"

    fun movieDetail(movieId: Int) = "movie_detail/$movieId"
}