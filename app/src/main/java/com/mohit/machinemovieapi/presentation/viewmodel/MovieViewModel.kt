package com.mohit.machinemovieapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.machinemovieapi.domain.model.Movie
import com.mohit.machinemovieapi.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortOrder = MutableStateFlow("popularity.desc")
    val sortOrder = _sortOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _isDetailsLoading = MutableStateFlow(false)
    val isDetailsLoading = _isDetailsLoading.asStateFlow()

    private val _isError = MutableStateFlow("")
    val isError = _isError.asStateFlow()

    private var currentPage = 1
    private var totalPages = 1

    private var searchJob: Job? = null

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = ""

            currentPage = 1
            _movies.value = emptyList()

            try {
                val query = _searchQuery.value.trim()

                val result = if (query.isNotEmpty()) {
                    repository.searchMovies(query = query, page = 1)
                } else {
                    repository.getMovies(
                        page = 1,
                        sortBy = _sortOrder.value
                    )
                }

                _movies.value = result.movies
                totalPages = result.totalPages

            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMovieById(id: Int) {
        if (_selectedMovie.value?.id == id) return

        viewModelScope.launch {
            _isDetailsLoading.value = true
            _isError.value = ""

            try {
                _selectedMovie.value = repository.getMoviesById(id)
            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isDetailsLoading.value = false
            }
        }
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500.milliseconds)
            loadMovies()
        }
    }

    fun sortMovies(order: String) {
        _sortOrder.value = order
        loadMovies()
    }

    fun loadMoreMovies() {
        if (_isLoading.value || _isLoadingMore.value || currentPage >= totalPages) {
            return
        }

        viewModelScope.launch {
            _isLoadingMore.value = true

            try {
                val nextPage = currentPage + 1
                val query = _searchQuery.value.trim()

                val result = if (query.isNotEmpty()) {
                    repository.searchMovies(query = query, page = nextPage)
                } else {
                    repository.getMovies(
                        page = nextPage,
                        sortBy = _sortOrder.value
                    )
                }

                _movies.value += result.movies
                currentPage = nextPage
                totalPages = result.totalPages

            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}