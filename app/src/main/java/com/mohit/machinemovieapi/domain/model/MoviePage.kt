package com.mohit.machinemovieapi.domain.model

data class MoviePage(
    val movies: List<Movie>,
    val totalPages: Int
)