package com.mohit.machinemovieapi.data.remote.dto

data class MovieDto(
    val id: Int,
    val genre_ids: List<Int>,
    val title: String,
    val overview: String,
    val poster_path: String,
)