package com.team1.wat2watch.ui.match

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.team1.wat2watch.data.api.RetrofitInstance
import utils.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MatchViewModel: ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies // Exposed for the UI layer

    private val _undoSwipe = MutableStateFlow(false)
    val undoSwipe: StateFlow<Boolean> = _undoSwipe

    private val _showInfoModal = MutableStateFlow(false)
    val showInfoModal: StateFlow<Boolean> = _showInfoModal

    private val _username = MutableStateFlow<String>("User")
    val username: StateFlow<String> = _username

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    init {
        loadUserData()
    }

    fun triggerUndo() {
        _undoSwipe.value = true
    }

    fun resetTriggerUndo() {
        _undoSwipe.value = false
    }

    fun showInfoModal() {
        _showInfoModal.value = true
    }

    fun hideInfoModal() {
        _showInfoModal.value = false
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _username.value = document.getString("username") ?: "User"
                    }
                }
        }
    }

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
}