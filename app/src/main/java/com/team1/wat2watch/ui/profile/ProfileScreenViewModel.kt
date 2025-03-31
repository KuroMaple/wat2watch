package com.team1.wat2watch.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.team1.wat2watch.ui.login.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileScreenViewModel : ViewModel() {
    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _creationDate = MutableLiveData<String>()
    val creationDate: LiveData<String> = _creationDate

    private val _isSignedOut = MutableLiveData<Boolean>(false)
    val isSignedOut: LiveData<Boolean> = _isSignedOut

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val _signOutEvent = MutableLiveData<Boolean>()
    val signOutEvent: LiveData<Boolean> = _signOutEvent

    private val _signOutSuccess = MutableLiveData<Boolean>()
    val signOutSuccess: LiveData<Boolean> = _signOutSuccess

    private val loginViewModel = LoginViewModel()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // First set email directly from Auth
            _email.value = currentUser.email ?: "No email available"

            // Then fetch the username from Firestore
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _username.value = document.getString("username") ?: "User"

                        // Get the dateJoined timestamp
                        val timestamp = document.getTimestamp("dateJoined")
                        if (timestamp != null) {
                            val date = timestamp.toDate()
                            val formatter = SimpleDateFormat("MM/yy", Locale.US)
                            _creationDate.value = formatter.format(date)
                        } else {
                            _creationDate.value = "12/24" // Default fallback
                        }
                    } else {
                        _username.value = "User" // Default value if no username found
                        _creationDate.value = "12/24" // Default fallback
                    }
                }
                .addOnFailureListener {
                    _username.value = "User" // Default value if error
                    _creationDate.value = "12/24" // Default fallback
                }
        } else {
            _username.value = "Not logged in"
            _email.value = "Not logged in"
            _creationDate.value = "12/24" // Default fallback
        }
    }

    fun signOut() {
        try {
            Log.d("ProfileScreenViewModel", "Signing out user")
            auth.signOut()

            // Log authentication state after sign out
            Log.d("ProfileScreenViewModel", "After signOut, user is null: ${auth.currentUser == null}")

            // Force the UI update
            _signOutEvent.postValue(true)
            _signOutSuccess.postValue(true)
        } catch (e: Exception) {
            Log.e("ProfileScreenViewModel", "Error signing out: ${e.message}")
            _signOutSuccess.postValue(false)
        }
    }
}
