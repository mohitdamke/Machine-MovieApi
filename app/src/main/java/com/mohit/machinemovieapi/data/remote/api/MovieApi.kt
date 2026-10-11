package com.mohit.machinemovieapi.data.remote.api

import com.mohit.machinemovieapi.data.remote.dto.MovieDto
import com.mohit.machinemovieapi.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
//    --url 'https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc' \

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("include_adult") includeAdult : Boolean = false,
        @Query("include_video") includeVideo : Boolean = false,
        @Query("language") language : String = "en-US",
        @Query("page") page : Int = 1,
        @Query("sort_by") sortBy : String = "popularity.desc",
    ): MovieResponseDto

//    --url 'https://api.themoviedb.org/3/search/movie?query=backdoor&include_adult=false&language=en-US&page=1' \


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query : String ,
        @Query("include_adult") includeAdult : Boolean = false,
        @Query("language") language : String = "en-US",
        @Query("page") page : Int = 1,
    ): MovieResponseDto

//    --url 'https://api.themoviedb.org/3/movie/222?language=en-US' \


    @GET("movie/{id}")
    suspend fun getMoviesById(
        @Path("id") id : Int ,
        @Query("language") language : String = "en-US",
    ): MovieDto















}