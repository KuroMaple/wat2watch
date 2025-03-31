package com.team1.wat2watch.ui.watchlist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.team1.wat2watch.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.Movie
import wat2watch.utils.FirestoreHelper
import wat2watch.utils.FirestoreHelper.getUserWatchlist
import wat2watch.utils.FirestoreHelper.removeFromWatchList

class WatchlistViewModel : ViewModel() {
//    private val _movies = MutableStateFlow(
//        listOf(
//            Movie(1, "Inception", 2010, "Sci-Fi", 8.8f, R.drawable.image18),
//            Movie(2, "The Dark Knight", 2008, "Action", 9.0f, R.drawable.image19),
//            Movie(3, "Interstellar", 2014, "Sci-Fi", 8.6f, R.drawable.image17),
//            Movie(4, "Titanic", 1997, "Romance", 7.9f, R.drawable.image18),
//            Movie(5, "The Godfather", 1972, "Crime", 9.2f, R.drawable.image17),
//            Movie(6, "Inception", 2010, "Sci-Fi", 8.8f, R.drawable.image18),
//            Movie(7, "The Dark Knight", 2008, "Action", 9.0f, R.drawable.image19),
//            Movie(8, "Interstellar", 2014, "Sci-Fi", 8.6f, R.drawable.image17),
//            Movie(9, "Titanic", 1997, "Romance", 7.9f, R.drawable.image18),
//            Movie(10, "The Godfather", 1972, "Crime", 9.2f, R.drawable.image17)
//        )
//    )

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies



    init {
        fetchUserWatchlist()
    }

    /*
    * Get user watchlist from db and convert to correct format
    * */
    fun fetchUserWatchlist() {
        getUserWatchlist(
            onSuccess = { movieItemList ->
                _movies.value = movieItemList
            },
            onFailure = { exception ->
                Log.e("WatchlistViewModel", "Failed to fetch watchlist: ${exception.message}")
            }
        )

        Log.d("WatchlistViewModel", "Fetched watchlist: ${_movies.value}")
    }

    fun getMovieById(movieId: Int): Movie? {
        return _movies.value.find { it.id == movieId }
    }

    fun removeFromWatchlist(movieId: String) {
        removeFromWatchList(movieId)
        _movies.value = _movies.value.filter { it.id.toString() != movieId }
    }

}


