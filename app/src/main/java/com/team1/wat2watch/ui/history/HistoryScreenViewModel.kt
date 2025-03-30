package com.team1.wat2watch.ui.history

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
import wat2watch.utils.FirestoreHelper.getUserMatchHistory
import wat2watch.utils.FirestoreHelper.getUserWatchlist
import wat2watch.utils.FirestoreHelper.removeFromWatchList

// Data class to structure history items for UI
data class HistoryItemData(
    val names: String,
    val movie: String,
    val timestamp: String,
    val imageUrl: String
)

class HistoryScreenViewModel : ViewModel() {

    private val _historyItems = MutableStateFlow<List<HistoryItemData>>(emptyList())
    val historyItems: StateFlow<List<HistoryItemData>> = _historyItems

    fun fetchUserMatchHistory(userId: String) {
        getUserMatchHistory(
            userId,
            onSuccess = { matchHistoryList ->
                val mappedHistory = matchHistoryList.mapNotNull { match ->
                    match.selectedMovie.poster_path?.let {
                        HistoryItemData(
                            names = match.users.joinToString(", "),
                            movie = match.selectedMovie.title,
                            timestamp = match.selectedMovie.addedOn,
                            imageUrl = it
                        )
                    }
                }

                // If match history is empty, use dummy data
                _historyItems.value = if (mappedHistory.isEmpty()) getDummyData() else mappedHistory
            },
            onFailure = { e ->
                Log.e("HistoryScreenViewModel", "Failed to fetch match history: ${e.message}")

                _historyItems.value = getDummyData()
            }
        )
    }

    private fun getDummyData(): List<HistoryItemData> {
        return listOf(
            HistoryItemData(
                names = "Alice, Bob, Charlie",
                movie = "Inception",
                timestamp = "2025-03-28",
                imageUrl = "/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"
            ),
            HistoryItemData(
                names = "Dave, Eve, Frank",
                movie = "Interstellar",
                timestamp = "2025-03-27",
                imageUrl = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
            ),
            HistoryItemData(
                names = "Grace, Hank, Ivy",
                movie = "The Dark Knight",
                timestamp = "2025-03-26",
                imageUrl = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
            )
        )
    }
}
