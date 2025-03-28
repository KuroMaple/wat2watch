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
    fun setCode(newCode: String) {
        _code.value = newCode
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
            Log.e("HomeViewModel", "Code input is null or blank")
            return
        }

        viewModelScope.launch {
            try {
                val sessionId = FirestoreHelper.getSessionIdFromAliasAsync(sessionAlias = targetSessionId)
                val currentUserUsername = FirestoreHelper.joinSessionWithIdAsync(
                    sessionId
                )

                val sessionInfo = FirestoreHelper.getSessionInfoAsync(
                    sessionId
                )


                Log.d("HomeViewModel", "$currentUserUsername joined session " +
                        "${sessionInfo.sessionId} successfully")

                // Update Joiner's ViewModel fields
                matchViewModel.setSolo(false)
                matchViewModel.setSessionID(sessionInfo.sessionId)
                sessionInfo.users.forEach {
                    matchViewModel.addParticipant(it)
                }
                matchViewModel.setSessionAlias(sessionInfo.sessionAlias)
                navController.navigate("match")
            }
            catch (e: Exception) {
                Log.e("HomeViewModel", "Error joining party: ${e.message}")
            }
        }

    }

}