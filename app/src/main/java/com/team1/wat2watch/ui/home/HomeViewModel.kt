package com.team1.wat2watch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel {
    private val _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    init {
        loadUserData()
    }

    fun setCode(newCode: String) {
        _code.value = newCode
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Fetch the username from Firestore
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _username.value = document.getString("username") ?: "User"
                    } else {
                        _username.value = "User" // Default value if no username found
                    }
                }
                .addOnFailureListener {
                    _username.value = "User" // Default value if error
                }
        } else {
            _username.value = "User" // Default value if not logged in
        }
    }
}