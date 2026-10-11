package com.mohit.machinemovieapi.presentation.screens

import android.R.attr.label
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinemovieapi.presentation.viewmodel.MovieViewModel

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val movies by viewModel.movies.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()

    Column(
        Modifier.fillMaxSize()
            .padding(16.dp)) {

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::searchMovies,
            label = { Text("Search movies") }
        )

        Row {
            Button(onClick = { viewModel.sortMovies("popularity.desc") }) {
                Text("Popular")
            }

            Button(onClick = { viewModel.sortMovies("popularity.asc") }) {
                Text("Least Popular")
            }
        }

        if (loading) {
            CircularProgressIndicator()
        } else if (error.isNotEmpty()) {
            Text(error)
            Button(onClick = { viewModel.loadMovies() }) {
                Text("Retry")
            }
        } else if (movies.isEmpty()) {
            Text("No movies found")
        } else {
            LazyColumn {
                items(movies) { movie ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onMovieClick(movie.id) }
                            .padding(12.dp)
                    ) {
                        Text(movie.title)
                        Text(movie.overview)
                    }

                    HorizontalDivider()
                }

                item {
                    Button(
                        onClick = { viewModel.loadMoreMovies() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Load More")
                    }
                }
            }
        }
    }
}