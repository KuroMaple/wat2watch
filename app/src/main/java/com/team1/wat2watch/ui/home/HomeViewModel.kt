package com.team1.wat2watch.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.team1.wat2watch.ui.match.MatchViewModel
import kotlinx.coroutines.launch
import wat2watch.utils.FirestoreHelper

class HomeViewModel: ViewModel() {
    // Input area for session code
    private val _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun setUsername(userName: String){
        _username.value = userName
    }

    init {
        fetchUsername()
    }

    fun setCode(newCode: String) {
        _code.value = newCode
        // Clear error when user types
        _errorMessage.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun onCreatePartyClick(
        navController: NavController,
        matchViewModel: MatchViewModel
    ) {
        viewModelScope.launch {
            try {
                val sessionId = FirestoreHelper.createSessionAsync()
                Log.d("HomeViewModel", "Session created successfully: $sessionId")

                val currentUserUsername = FirestoreHelper.joinSessionWithIdAsync(sessionId)
                Log.d("HomeViewModel", "$currentUserUsername joined session successfully")

                val sessionInfo = FirestoreHelper.getSessionInfoAsync(sessionId)
                Log.d("HomeViewModel", "${sessionInfo.sessionAlias} session's info fetched successfully")

                // Update ViewModel fields
                matchViewModel.setSolo(false)
                matchViewModel.setSessionID(sessionId)
                matchViewModel.addParticipant(currentUserUsername)
                matchViewModel.setSessionAlias(sessionInfo.sessionAlias)

                // Navigate to the match screen
                navController.navigate("match")

            }
            catch (e: Exception) {
                Log.e("HomeViewModel", "Error creating party: ${e.message}")
            }
        }
    }

    fun onJoinPartyClick(
        navController: NavController,
        matchViewModel: MatchViewModel
    ){
        val targetSessionId = _code.value
        if (targetSessionId.isNullOrBlank()) {
            _errorMessage.value = "Please enter a party code"
            return
        }

        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Attempting to join party with code: $targetSessionId")

                val sessionId = FirestoreHelper.getSessionIdFromAliasAsync(sessionAlias = targetSessionId)
                Log.d("HomeViewModel", "Session ID retrieved: $sessionId")

                if (sessionId.isBlank()) {
                    Log.d("HomeViewModel", "Party does not exist - blank session ID")
                    _errorMessage.value = "Party does not exist"
                    return@launch
                }

                try {
                    val currentUserUsername = FirestoreHelper.joinSessionWithIdAsync(sessionId)
                    Log.d("HomeViewModel", "User '$currentUserUsername' joined session")

                    val sessionInfo = FirestoreHelper.getSessionInfoAsync(sessionId)
                    Log.d("HomeViewModel", "Session info retrieved: ${sessionInfo.sessionAlias} with ${sessionInfo.users.size} users")

                    matchViewModel.setSolo(false)
                    matchViewModel.setSessionID(sessionInfo.sessionId)

                    // Clear participants first to avoid duplicates
                    sessionInfo.users.forEach { username ->
                        Log.d("HomeViewModel", "Adding participant: $username")
                        matchViewModel.addParticipant(username)
                    }

                    matchViewModel.setSessionAlias(sessionInfo.sessionAlias)

                    Log.d("HomeViewModel", "Navigating to match screen")
                    navController.navigate("match")
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error during session join process: ${e.message}", e)
                    _errorMessage.value = "Error joining party: ${e.message ?: "Unknown error"}"
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error joining party: ${e.message}", e)
                _errorMessage.value = "Party does not exist"
            }
        }
    }

    private fun fetchUsername() {
        viewModelScope.launch {
            try {
                val currUser = FirestoreHelper.getUserUsernameAsync()
                this@HomeViewModel.setUsername(currUser)
            }
            catch (e: Exception) {
                Log.e("HomeViewModel", "Error Fetching Username: ${e.message}")
            }
        }
    }
}