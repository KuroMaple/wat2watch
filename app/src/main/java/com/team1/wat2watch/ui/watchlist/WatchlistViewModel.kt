package com.team1.wat2watch.ui.watchlist

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
import com.team1.wat2watch.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Movie(
    val title: String,
    val year: Int,  // Changed to Int for proper sorting
    val genre: String,
    val rating: Float, // ⭐ Added rating
    val imageResId: Int
)


class WatchlistViewModel : ViewModel() {
    private val _movies = MutableStateFlow(
        listOf(
            Movie("Inception", 2010, "Sci-Fi", 8.8f, R.drawable.image18),
            Movie("The Dark Knight", 2008, "Action", 9.0f, R.drawable.image19),
            Movie("Interstellar", 2014, "Sci-Fi", 8.6f, R.drawable.image17),
            Movie("Titanic", 1997, "Romance", 7.9f, R.drawable.image18),
            Movie("The Godfather", 1972, "Crime", 9.2f, R.drawable.image17),
            Movie("Inception", 2010, "Sci-Fi", 8.8f, R.drawable.image18),
            Movie("The Dark Knight", 2008, "Action", 9.0f, R.drawable.image19),
            Movie("Interstellar", 2014, "Sci-Fi", 8.6f, R.drawable.image17),
            Movie("Titanic", 1997, "Romance", 7.9f, R.drawable.image18),
            Movie("The Godfather", 1972, "Crime", 9.2f, R.drawable.image17)
        )
    )

    val movies: StateFlow<List<Movie>> = _movies
}


