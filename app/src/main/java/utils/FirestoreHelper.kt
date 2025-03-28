package wat2watch.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.ceil
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.tasks.await
import utils.Movie
import utils.Session
import utils.generateSessionIdAlias
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirestoreHelper {

    data class MatchHistoryItem(
        val sessionId: String = "",
        val selectedMovie: Movie = Movie(),
        val users: List<String> = emptyList()
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
            "dateJoined" to FieldValue.serverTimestamp()
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
        Log.d("FirestoreHelper", "Fetching user's username before")
        val uid = getCurrentUserUid() ?: return
        Log.d("FirestoreHelper", "Fetching user's username after")
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d("FirestoreHelper", "Fetched user's username in if statement")
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
    // TODO: Pass blank run time
    fun addToWatchList(movieId: String, title: String, release_date: String,
                       poster_path: String, overview: String, adult: Boolean) {
        val uid = getCurrentUserUid() ?: return
        val watchListRef = db.collection("users").document(uid).collection("watchList")

        val movieData = hashMapOf(
            "id" to movieId,
            "title" to title,
            "release_date" to release_date,
            "poster_path" to poster_path,
            "overview" to overview,
            "adult" to adult,
            "addedOn" to System.currentTimeMillis().toString()
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
     * Gets all movies from user's watchlist, they will be in a list of Movie structure
     */

    fun getUserWatchlist(onSuccess: (List<Movie>) -> Unit, onFailure: (Exception) -> Unit) {
        val currentUser = getCurrentUserUid() ?: return
        val watchListRef = db.collection("users").document(currentUser).collection("watchList")

        watchListRef.get()
            .addOnSuccessListener { result ->
                val watchlist = mutableListOf<Movie>()
                for (document in result) {
                    val id = document.getString("id")?.toIntOrNull() ?: -1
                    val title = document.getString("title") ?: ""
                    val release_date = document.getString("release_date") ?: ""
//                    val runtime = document.getString("runtime") ?: ""
                    val poster_path = document.getString("poster_path") ?: ""
                    val overview = document.getString("overview") ?: ""
                    val adult = document.getBoolean("adult") ?: false
                    val addedOn = document.getString("addedOn") ?: ""

                    val movie = Movie(id.toInt(), title, release_date, poster_path, overview, adult, addedOn)
                    watchlist.add(movie)
                }
                Log.d("FirestoreHelper", "Fetched watchlist for User")
                onSuccess(watchlist)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error fetching watchlist: ${e.message}")
                onFailure(e)
            }
    }

    // Remove function
    fun removeFromWatchList(movieId: String) {
        val uid = getCurrentUserUid() ?: return
        val watchListRef = db.collection("users").document(uid).collection("watchList")

        watchListRef.document(movieId).delete()
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "Movie removed from Watchlist")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error removing movie from watchlist: ${e.message}")
            }
    }

    /**
     * -----------------------------------------------------------------------------------------
     * Adds an ended session to the user's match history (should be called from session end)
     */

    fun addMatchHistory(sessionId: String, movie: Movie, users: List<String>) {
        val uid = getCurrentUserUid() ?: return
        val matchHistoryRef = db.collection("users").document(uid).collection("matchHistory")

        val matchData = hashMapOf(
            "sessionId" to sessionId,
            "selectedMovie" to hashMapOf(
                "id" to movie.id,
                "title" to movie.title,
                "release_date" to movie.release_date,
//                "runtime" to movie.,
                "poster_path" to movie.poster_path,
                "overview" to movie.overview,
                "adult" to movie.adult,
                "addedOn" to System.currentTimeMillis()
            ),
            "users" to users
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

    private fun getUserMatchHistory(userId: String, onSuccess: (List<MatchHistoryItem>) -> Unit, onFailure: (Exception) -> Unit) {
        val matchHistoryRef = db.collection("users").document(userId).collection("matchHistory")

        matchHistoryRef.get()
            .addOnSuccessListener { result ->
                val matchHistoryList = mutableListOf<MatchHistoryItem>()
                for (document in result) {
                    val sessionId = document.getString("sessionId") ?: ""

                    val movieData = document.get("selectedMovie") as? Map<String, Any> ?: emptyMap()
                    val movie = Movie(
                        id = movieData["id"] as? Int ?: 0,
                        title = movieData["title"] as? String ?: "",
                        release_date = movieData["release_date"] as? String ?: "",
                        poster_path = movieData["poster_path"] as? String ?: "",
                        overview = movieData["overview"] as? String ?: "",
                        adult = movieData["adult"] as? Boolean ?: false,
                        addedOn = movieData["addedOn"]?.toString() ?: ""
                    )

                    val users = document.get("users") as? List<String> ?: emptyList()


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

    private fun createSession(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        // Creating a random ID using firestore
        val sessionId = FirebaseFirestore.getInstance().collection("sessions").document().id
        val sessionAlias = generateSessionIdAlias(sessionId)
        val sessionData = hashMapOf(
            "sessionId" to sessionId,
            "users" to emptyList<String>(),
            "swipes" to hashMapOf<String, Map<String, Any>>(), // Dictionary containing key: movie ID and val: Movie and swipeCount
            "countGoal" to 1, // Default count goal for a single user
            "active" to true,
            "sessionAlias" to sessionAlias
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


    private fun joinSessionById(sessionId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val sessionRef = firestore.collection("sessions").document(sessionId)
        val uid = getCurrentUserUid() ?: return

        // Fetch the username from Firestore
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { userDoc ->
                val username = userDoc.getString("username") ?: return@addOnSuccessListener

                sessionRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val users = document.get("users") as? List<String> ?: emptyList()
                        if (users.contains(username)) {
                            // Prevent duplicate adds
                            Log.d("FirestoreHelper", "User $username already in session $sessionId")
                            onSuccess(username)

                        }
                        val newCountGoal = ceil((users.size + 1) / 2.0).toInt()  // Majority
                        Log.d("FirestoreHelper", "New count goal: $newCountGoal")
                        if (users.size >= 8) {
                            onFailure(Exception("Session is full"))
                        }

                        sessionRef.update(
                            "users", FieldValue.arrayUnion(username), // Store username instead of UID
                            "countGoal", newCountGoal
                        )
                            .addOnSuccessListener {

                                Log.d("FirestoreHelper", "User $username joined session $sessionId")
                                onSuccess(username)
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
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error fetching username: ${e.message}")
                onFailure(e)
            }
    }

    private fun logSwipe(sessionId: String, movie: Movie, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val sessionRef = FirebaseFirestore.getInstance().collection("sessions").document(sessionId)

        sessionRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val swipes = document.get("swipes") as? MutableMap<String, MutableMap<String, Any>> ?: mutableMapOf()
                val countGoal = document.get("countGoal") as? Int ?: 1

                val movieId = movie.id.toString()
                val currentSwipeData = swipes[movieId] ?: mutableMapOf("swipeCount" to 0, "movie" to mutableMapOf<String, Any>())

                val newCount = (currentSwipeData["swipeCount"] as? Long ?: 0) + 1

                val movieData = mutableMapOf(
                    "id" to movie.id,
                    "title" to movie.title,
                    "release_date" to movie.release_date,
                    "poster_path" to movie.poster_path,
                    "overview" to movie.overview,
                    "adult" to movie.adult,
                    "addedOn" to movie.addedOn
                )

                currentSwipeData["swipeCount"] = newCount
                currentSwipeData["movie"] = movieData
                swipes[movieId] = currentSwipeData

                sessionRef.update("swipes", swipes)
                    .addOnSuccessListener {
                        Log.d("FirestoreHelper", "Swipe logged for movie $movieId in session $sessionId")

                        // Check if the session should end based on the countGoal
                        if (newCount > countGoal) {
                            val updates = hashMapOf<String, Any>(
                                "active" to false,  // Mark session as inactive
                                "selectedMovie" to movieData // Store full movie details
                            )
                            sessionRef.update(updates).addOnSuccessListener {
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


    // returns sessionId, user names (in string, just display them as is), and isActive signal
    private fun getSessionInfo(sessionId: String, onSuccess: (Session) -> Unit, onFailure: (Exception) -> Unit) {
        val sessionRef = FirebaseFirestore.getInstance().collection("sessions").document(sessionId)

        sessionRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val curSession = document.getString("sessionId") ?: ""
                val userNames = document.get("users") as? List<String> ?: emptyList()
                val isActive = document.getBoolean("active") ?: false
                val countGoal = document.get("countGoal") as? Int ?: 1
                val swipes = document.get("swipes") as? Map<String, Map<String, Any>> ?: emptyMap()
                val sessionAlias = document.getString("sessionAlias") ?: ""
                // Return session object
                val session = Session(isActive, userNames, swipes, countGoal, curSession, sessionAlias)
                onSuccess(session)
            } else {
                onFailure(Exception("Session not found"))
            }
        }.addOnFailureListener { e ->
            onFailure(e)
        }
    }

    private fun endSession(sessionId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val sessionRef = FirebaseFirestore.getInstance().collection("sessions").document(sessionId)
        sessionRef.update("active", false).addOnSuccessListener {
            Log.d("FirestoreHelper", "Session $sessionId ended")
            onSuccess()
        }.addOnFailureListener(onFailure)

    }

    private fun getSessionIdFromAlias(
        sessionAlias: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("sessions")
            .whereEqualTo("sessionAlias", sessionAlias) // Query for alias field
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    onFailure(Exception("Session not found"))
                    return@addOnSuccessListener
                }

                // Get the first matching document ID (which is the session ID)
                val sessionId = querySnapshot.documents.first().id
                onSuccess(sessionId)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }


    /*
    * ONLY THE BELOW FUNCTIONS SHOULD BE USED OUTSIDE OF HELPER
    * Encapsulating functions in suspend wrappers for better performance and readability
    * */
    suspend fun createSessionAsync(): String = suspendCoroutine { cont ->
        createSession(
            onSuccess = { cont.resume(it) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

    suspend fun getSessionIdFromAliasAsync(sessionAlias: String): String = suspendCoroutine { cont ->
        getSessionIdFromAlias(
            sessionAlias,
            onSuccess = { cont.resume(it) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

    suspend fun joinSessionWithIdAsync(sessionId: String): String = suspendCoroutine { cont ->
        joinSessionById(
            sessionId,
            onSuccess = { username -> cont.resume(username) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

    suspend fun getSessionInfoAsync(sessionId: String): Session = suspendCoroutine { cont ->
        getSessionInfo(
            sessionId,
            onSuccess = { session -> cont.resume(session) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

    suspend fun endSessionAsync(sessionId: String): Unit = suspendCoroutine { cont ->
        endSession(
            sessionId,
            onSuccess = { cont.resume(Unit) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

    suspend fun logSwipeAsync(sessionId: String, movie: Movie): String = suspendCoroutine { cont ->
        logSwipe(
            sessionId,
            movie,
            onSuccess = { cont.resume(sessionId) },
            onFailure = { cont.resumeWithException(it) }
        )
    }

}
