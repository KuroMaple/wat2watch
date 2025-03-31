package com.team1.wat2watch.ui.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.team1.wat2watch.data.api.RetrofitInstance
import utils.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import wat2watch.utils.FirestoreHelper

class MatchViewModel: ViewModel() {
    private val _sessionAlias = MutableStateFlow<String?>(null)
    val sessionAlias: StateFlow<String?> = _sessionAlias
    fun setSessionAlias(alias: String) {
        _sessionAlias.value = alias
    }
    private fun resetSessionAlias() {
        _sessionAlias.value = null
    }

    private val _sessionID = MutableStateFlow<String?>(null)
    val sessionID: StateFlow<String?> = _sessionID
    fun setSessionID(id: String) {
        _sessionID.value = id
    }
    private fun resetSessionID() {
        _sessionID.value = null
    }



    private val _participants = MutableStateFlow<List<String>>(emptyList())
    val participants: StateFlow<List<String>> = _participants
    fun addParticipant(name: String) {
        val currentList = _participants.value.toMutableList()
        currentList.add(name)
        _participants.value = currentList
    }
    private fun resetParticipants() {
        _participants.value = emptyList()
    }

    private val _isSoloSwipe = MutableStateFlow(true)
    val isSolo: StateFlow<Boolean> = _isSoloSwipe
    fun setSolo(isSolo: Boolean) {
        _isSoloSwipe.value = isSolo
    }
    private fun resetSolo() {
        _isSoloSwipe.value = true
    }

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies // Exposed for the UI layer

    private val _undoSwipe = MutableStateFlow(false)
    val undoSwipe: StateFlow<Boolean> = _undoSwipe
    fun triggerUndo() {
        _undoSwipe.value = true
    }
    fun resetTriggerUndo() {
        _undoSwipe.value = false
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

    /*
     * Cleanup ViewModel and reset active session status
     */
    fun handleEndSession(sessionId: String?, navController: NavController){

        viewModelScope.launch {
            try {
                if (sessionId == null) {
                    throw Exception("Session ID is null")
                }

                FirestoreHelper.endSessionAsync(sessionId)

                val sessionInfo = FirestoreHelper.getSessionInfoAsync(sessionId)
                if (sessionInfo.isActive) {
                    throw Exception("Session did not end")
                }
                if (sessionInfo.selectedMovie != null){
                    FirestoreHelper.addMatchHistory(
                        sessionId = sessionInfo.sessionId,
                        movie = sessionInfo.selectedMovie,
                        users = sessionInfo.users
                    )
                }
                else {
                    Log.d("MatchViewModel", "No selected movie to add to match history")
                }


                Log.d("MatchViewModel", "${sessionInfo.sessionAlias} session ended successfully")
                navController.navigate("history")
            }
            catch (e: Exception) {
                Log.e("MatchViewModel", "Error ending session: ${e.message}")
            }
        }

        this.resetParticipants()
        this.resetSessionID()
        this.resetSessionAlias()
        this.resetSolo()
        navController.navigate("home")
    }

    /*
    * Solo swipe should just add to watch list
    * for group swipe, call
    * */
    fun handleSwipeRight(targetMovieIndex: Int, navController: NavController){
        if(_isSoloSwipe.value){
            FirestoreHelper.addToWatchList(
                movieId = _movies.value[targetMovieIndex].id.toString(),
                title = _movies.value[targetMovieIndex].title,
                release_date = _movies.value[targetMovieIndex].release_date,
                poster_path = _movies.value[targetMovieIndex].poster_path ?: "",
                overview = _movies.value[targetMovieIndex].overview,
                adult = _movies.value[targetMovieIndex].adult
            )
        }
        else {
            viewModelScope.launch {
                try {
                    FirestoreHelper.logSwipeAsync(
                        sessionId = _sessionID.value ?: throw Exception("Session ID is null"),
                        movie = _movies.value[targetMovieIndex],
                    )

                    val sessionInfo = FirestoreHelper.getSessionInfoAsync(
                        _sessionID.value ?: throw Exception("Session ID is null")
                    )

                    if (!sessionInfo.isActive){
                        handleEndSession(sessionInfo.sessionId, navController)
                    }
                }
                catch (e: Exception) {
                    Log.e("MatchViewModel", "Error Swiping Right in Group session: ${e.message}")
                }
            }
        }
    }
}