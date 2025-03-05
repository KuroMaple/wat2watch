package com.team1.wat2watch.ui.match

import androidx.lifecycle.ViewModel
import com.team1.wat2watch.data.api.RetrofitInstance
import com.team1.wat2watch.data.model.Movie
import com.team1.wat2watch.data.model.MovieResponse
import com.team1.wat2watch.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchViewModel: ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies // Exposed for the UI layer

    // Handle errors and loading state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    suspend fun fetchMovies(
        apiKey: String,
        startYear: Int = 2020,
        endYear: Int = 2024,
        genres: List<Int> = listOf(28, 12), // 28: Action, 12: Adventure
        pages: Int = 3,
    )
    {
        val movies = mutableListOf<Movie>()
        val genreStr = genres.joinToString(",")

        for (page in 1..pages) {
            try {
                val response = RetrofitInstance.api.fetchMovies(
                    apiKey = apiKey,
                    startDate = "$startYear-01-01",
                    endDate = "$endYear-12-31",
                    genres = genreStr,
                    page = page
                )
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        println("Movies fetched successfully")
                        println(it)
                        movies.addAll(it)
                    }}
                else {
                    _movies.value = emptyList()
                    println("Error fetching movies: ${response.code()}")
                }

                _movies.value = movies
            } catch (e: Exception) {
                _movies.value = emptyList()
                println("Error fetching movies: ${e.message}")
            }
        }

    }

    fun removeTopMovie() {
        //TODO: Modify this function to discern left from right swipe and add to watchlist
        _movies.value = _movies.value.drop(1)
    }
}