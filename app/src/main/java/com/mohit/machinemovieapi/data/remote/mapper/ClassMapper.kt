package com.mohit.machinemovieapi.data.remote.mapper

import com.mohit.machinemovieapi.data.remote.dto.MovieDto
import com.mohit.machinemovieapi.domain.model.Movie

fun MovieDto.toMovie() : Movie{
    return Movie(
        id = id,
        genre_ids = genre_ids,
        title = title,
        overview = overview,
        poster_path = poster_path
    )

}

