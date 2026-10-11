package com.mohit.machinemovieapi.domain.model

data class Movie(
    val id: Int,
    val genre_ids: List<Int>?,
    val title: String,
    val overview: String,
    val poster_path: String?,
)