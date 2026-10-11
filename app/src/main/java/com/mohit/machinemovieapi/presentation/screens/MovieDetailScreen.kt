package com.mohit.machinemovieapi.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinemovieapi.presentation.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val movie by viewModel.selectedMovie.collectAsState()
    val loading by viewModel.isDetailsLoading.collectAsState()
    val error by viewModel.isError.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.getMovieById(movieId)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else if (error.isNotEmpty()) {
            Text(error)
            Button(onClick = { viewModel.getMovieById(movieId) }) {
                Text("Retry")
            }
        } else {
            movie?.let {
                Text(it.title)
                Text(it.overview)
            }
        }
    }
}