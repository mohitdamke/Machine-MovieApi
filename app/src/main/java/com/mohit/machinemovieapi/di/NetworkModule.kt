package com.mohit.machinemovieapi.di

import com.mohit.machinemovieapi.data.remote.api.MovieApi
import com.mohit.machinemovieapi.data.repository.MovieRepositoryImpl
import com.mohit.machinemovieapi.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZGE5NTQ3ZTcxZGU3NzEwNTE1ZDcxZmNmY2FmYTA0MSIsIm5iZiI6MTY5NjQ5MDMxOC43MDIwMDAxLCJzdWIiOiI2NTFlNjM0ZWVhODRjNzAwZWI5YmM0ZTgiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.XeA-Ma40ANDj9DmLb1fctN7afdbSc-iGCG8DEwcNZjE")
                        .addHeader("accept", "application/json")
                        .build()
                )
            }
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(client: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}