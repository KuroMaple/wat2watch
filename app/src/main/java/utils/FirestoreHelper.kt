package wat2watch.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

object FirestoreHelper {
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
     * Adds a movie to the user's history.
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
            "addedOn" to System.currentTimeMillis() // Optional: To track when the movie was added
        )

        watchListRef.document(movieId).set(movieData)
            .addOnSuccessListener {
                Log.d("FirestoreHelper", "Movie Added to Watchlist")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHelper", "Error adding movie to watchlist: ${e.message}")
            }
    }


}
