package wat2watch.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.ceil
import com.google.firebase.firestore.FieldValue

object FirestoreHelper {

    data class MovieItem(
        val title: String = "",
        val year: String = "",
        val runtime: String = "",
        val poster: String = "",
        val summary: String = "",
        val addedOn: String = ""
    )

    data class UserInfo(
        val uid: String = "",
        val username: String = ""
    )

    data class MatchHistoryItem(
        val sessionId: String = "",
        val selectedMovie: MovieItem = MovieItem(),
        val users: List<UserInfo> = emptyList()
    )

    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Helper function to get current user's UID or call the failure callback.
     */
    private fun getCurrentUserUid(): String? {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            Log.e("FirestoreHelper", "No user logged in; UID not found")
        }
        return uid
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Saves a new user to Firestore.
     */
    fun addUserToFirestore(email: String, username: String) {
        val uid = getCurrentUserUid() ?: return
        val userMap = hashMapOf(
            "uid" to uid,
            "email" to email,
            "username" to username,
        )

        db.collection("users").document(uid)
            .set(userMap)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "User added to database successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error adding user to database: ${e.message}")
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Gets the current user's username from Firestore.
     * Call it like this
     * FirestoreHelper.getUserUsername(
     *             onSuccess = { username ->
     *                 // whatever needs to happen with the username
     *             },
     *             onFailure = { exception ->
     *                 Log.e("SignUpViewModel", "Failed to get username: ${exception.message}")
     *             }
     *         )
     */
    fun getUserUsername(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val uid = getCurrentUserUid() ?: return
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val username = document.getString("username") ?: ""
                    Log.d("FirestoreHelper", "Fetched user's username: $username")
                    onSuccess(username)
                } else {
                    Log.e("FirestoreHelper", "User not found in database")
                    onFailure(Exception("User document not found"))
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error getting user data: ${e.message}")
                onFailure(e) // Handle failure
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Adds a movie to the user's watchlist
     */

    fun addToWatchList(movieId: String, title: String, year: String, runtime: String, poster: String, summary: String) {
        val uid = getCurrentUserUid() ?: return
        val watchListRef = db.collection("users").document(uid).collection("watchList")

        val movieData = hashMapOf(
            "title" to title,
            "year" to year,
            "runtime" to runtime,
            "poster" to poster,
            "summary" to summary,
            "addedOn" to System.currentTimeMillis()
        )

        watchListRef.document(movieId).set(movieData)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "Movie Added to Watchlist")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error adding movie to watchlist: ${e.message}")
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Gets all movies from user's watchlist, they will be in a list of MovieItem structure
     */

    fun getUserWatchlist(userId: String, onSuccess: (List<MovieItem>) -> Unit, onFailure: (Exception) -> Unit) {
        val watchListRef = db.collection("users").document(userId).collection("watchList")

        watchListRef.get()
            .addOnSuccessListener { result ->
                val watchlist = mutableListOf<MovieItem>()
                for (document in result) {
                    val title = document.getString("title") ?: ""
                    val year = document.getString("year") ?: ""
                    val runtime = document.getString("runtime") ?: ""
                    val poster = document.getString("poster") ?: ""
                    val summary = document.getString("summary") ?: ""
                    val addedOn = document.getString("addedOn") ?: ""

                    val MovieItem = MovieItem(title, year, runtime, poster, summary, addedOn)
                    watchlist.add(MovieItem)
                }
                Log.d("FirestoreHelper", "Fetched watchlist for User")
                onSuccess(watchlist)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error fetching watchlist: ${e.message}")
                onFailure(e)
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Adds an ended session to the user's match history (should be called from session end)
     */

    fun addMatchHistory(sessionId: String, movie: MovieItem, users: List<UserInfo>) {
        val uid = getCurrentUserUid() ?: return
        val matchHistoryRef = db.collection("users").document(uid).collection("matchHistory")

        val matchData = hashMapOf(
            "sessionId" to sessionId,
            "selectedMovie" to hashMapOf(
                "title" to movie.title,
                "year" to movie.year,
                "runtime" to movie.runtime,
                "poster" to movie.poster,
                "summary" to movie.summary,
                "addedOn" to System.currentTimeMillis()
            ),
            "users" to users.map { user ->
                hashMapOf(
                    "uid" to user.uid,
                    "username" to user.username
                )
            }
        )

        matchHistoryRef.document(sessionId).set(matchData)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "Match history added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error adding match history: ${e.message}")
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Gets all matches from user's match history
     */

    fun getUserMatchHistory(userId: String, onSuccess: (List<MatchHistoryItem>) -> Unit, onFailure: (Exception) -> Unit) {
        val matchHistoryRef = db.collection("users").document(userId).collection("matchHistory")

        matchHistoryRef.get()
            .addOnSuccessListener { result ->
                val matchHistoryList = mutableListOf<MatchHistoryItem>()
                for (document in result) {
                    val sessionId = document.getString("sessionId") ?: ""

                    val movieData = document.get("selectedMovie") as? Map<String, Any> ?: emptyMap()
                    val movie = MovieItem(
                        title = movieData["title"] as? String ?: "",
                        year = movieData["year"] as? String ?: "",
                        runtime = movieData["runtime"] as? String ?: "",
                        poster = movieData["poster"] as? String ?: "",
                        summary = movieData["summary"] as? String ?: "",
                        addedOn = movieData["addedOn"]?.toString() ?: ""
                    )

                    val usersData = document.get("users") as? List<Map<String, Any>> ?: emptyList()
                    val users = usersData.map { userMap ->
                        UserInfo(
                            uid = userMap["uid"] as? String ?: "",
                            username = userMap["username"] as? String ?: ""
                        )
                    }

                    val matchHistoryItem = MatchHistoryItem(sessionId, movie, users)
                    matchHistoryList.add(matchHistoryItem)
                }
                Log.d("FirestoreHelper", "Fetched match history for User")
                onSuccess(matchHistoryList)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error fetching match history: ${e.message}")
                onFailure(e)
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Session Functions
     */

    fun createSession(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        // Creating a random ID using firestore
        val sessionId = FirebaseFirestore.getInstance().collection("sessions").document().id
        val sessionData = hashMapOf(
            "sessionId" to sessionId,
            "users" to emptyList<String>(),
            "swipes" to hashMapOf<String, Int>(), // Dictionary containing key: movie ID, val: swipe count
            "countGoal" to 1, // Default count goal for a single user
            "active" to true
        )

        FirebaseFirestore.getInstance().collection("sessions").document(sessionId)
            .set(sessionData)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "Session created: $sessionId")
                onSuccess(sessionId)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error creating session: ${e.message}")
                onFailure(e)
            }
    }

    fun joinSession(sessionId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val sessionRef = FirebaseFirestore.getInstance().collection("sessions").document(sessionId)
        val uid = getCurrentUserUid() ?: return

        sessionRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val users = document.get("users") as? List<String> ?: emptyList()
                val newCountGoal = ceil(users.size / 2.0).toInt()  // Majority

                sessionRef.update(
                    "users", FieldValue.arrayUnion(uid),
                    "countGoal", newCountGoal
                )
                    .addOnSuccessListener {
                        Log.d("FirestoreHelper", "User $uid joined session $sessionId")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirestoreHelper", "Error joining session: ${e.message}")
                        onFailure(e)
                    }
            } else {
                onFailure(Exception("Session not found"))
            }
        }.addOnFailureListener { e ->
            onFailure(e)
        }
    }

    fun logSwipe(sessionId: String, movieId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val sessionRef = FirebaseFirestore.getInstance().collection("sessions").document(sessionId)

        sessionRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val swipes = document.get("swipes") as? MutableMap<String, Long> ?: mutableMapOf()
                val countGoal = document.get("countGoal") as? Int ?: 1
                val newCount = (swipes[movieId] ?: 0) + 1

                sessionRef.update("swipes.$movieId", newCount)
                    .addOnSuccessListener {
                        Log.d("FirestoreHelper", "Swipe logged for movie $movieId in session $sessionId")

                        // Check if the session should end based on the countGoal
                        if (newCount >= countGoal) {
                            sessionRef.update("status", "ended", "selectedMovie", movieId)
                                .addOnSuccessListener {
                                    Log.d("FirestoreHelper", "Session $sessionId ended. Movie: $movieId")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FirestoreHelper", "Error ending session: ${e.message}")
                                }
                        }
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirestoreHelper", "Error logging swipe: ${e.message}")
                        onFailure(e)
                    }
            } else {
                onFailure(Exception("Session not found"))
            }
        }.addOnFailureListener { e ->
            onFailure(e)
        }
    }






}
