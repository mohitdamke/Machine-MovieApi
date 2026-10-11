package com.mohit.machinemovieapi.domain.repository

import com.mohit.machinemovieapi.domain.model.Movie
import com.mohit.machinemovieapi.domain.model.MoviePage

interface MovieRepository {

    suspend fun getMovies(
        page: Int = 1,
        sortBy: String = "popularity.desc",
    ): MoviePage

    suspend fun searchMovies(
        query: String,
        page: Int = 1,
    ): MoviePage

    suspend fun getMoviesById(
        id: Int,
    ): Movie
}