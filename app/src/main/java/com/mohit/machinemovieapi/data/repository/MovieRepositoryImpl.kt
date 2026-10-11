package com.mohit.machinemovieapi.data.repository

import com.mohit.machinemovieapi.data.remote.api.MovieApi
import com.mohit.machinemovieapi.data.remote.mapper.toMovie
import com.mohit.machinemovieapi.domain.model.Movie
import com.mohit.machinemovieapi.domain.model.MoviePage
import com.mohit.machinemovieapi.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovies(
        page: Int,
        sortBy: String
    ): MoviePage {
        val response = api.getMovies(
            page = page, sortBy = sortBy
        )
        return MoviePage(
            movies = response.results.map { it.toMovie() },
            totalPages = response.total_pages
        )
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): MoviePage {

        val response = api.searchMovies(query = query, page = page)
        return MoviePage(
            movies = response.results.map { it.toMovie() },
            totalPages = response.total_pages
        )

    }

    override suspend fun getMoviesById(
        id: Int,
    ): Movie {
        return api.getMoviesById(id).toMovie()
    }
}