package com.team1.wat2watch.ui.history

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.Movie
import wat2watch.utils.FirestoreHelper.getUserMatchHistory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Data class to structure history items for UI
data class HistoryItemData(
    val names: String,
    val movie: Movie,
    val timestamp: String,
)

class HistoryScreenViewModel : ViewModel() {

    private val _historyItems = MutableStateFlow<List<HistoryItemData>>(emptyList())
    val historyItems: StateFlow<List<HistoryItemData>> = _historyItems

    fun fetchUserMatchHistory() {
        getUserMatchHistory(
            onSuccess = { matchHistoryList ->
                val mappedHistory = matchHistoryList.mapNotNull { match ->
                    match.selectedMovie.poster_path?.let {
                        HistoryItemData(
                            names = match.users.joinToString(", "),
                            movie = match.selectedMovie,
                            timestamp = formatTimestamp(match.selectedMovie.addedOn.toLong()),
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

    fun formatTimestamp(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }

    private fun getDummyData(): List<HistoryItemData> {
        return listOf(
            HistoryItemData(
                names = "testuser, hassan@gmail.com",
                movie =  Movie(
                    title = "Sonic the Hedgehog 3",
                    release_date = "2024-12-19",
                    poster_path = "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
                    overview = "Sonic, Knuckles, and Tails reunite against a powerful new " +
                            "adversary, Shadow, a mysterious villain with powers unlike anything " +
                            "they have faced before. With their abilities outmatched in every way, " +
                            "Team Sonic must seek out an unlikely alliance in hopes of stopping " +
                            "Shadow and protecting the planet.",
                    adult = false,
                    addedOn = formatTimestamp("1743571200000".toLong()),
                    id = 0
                ),
                timestamp = formatTimestamp("1743571200000".toLong()),
            ),
//            HistoryItemData(
//                names = "Dave, Eve, Frank",
//                movie = "Interstellar",
//                timestamp = "2025-03-27",
//                imageUrl = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"
//            ),
//            HistoryItemData(
//                names = "Grace, Hank, Ivy",
//                movie = "The Dark Knight",
//                timestamp = "2025-03-26",
//                imageUrl = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
//            )
        )
    }


}
