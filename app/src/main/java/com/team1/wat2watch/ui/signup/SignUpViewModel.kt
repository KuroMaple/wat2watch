package com.team1.wat2watch.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

//import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess

    private val _signUpError = MutableLiveData<String?>()
    val signUpError: LiveData<String?> = _signUpError

    private val _navigationEvent = MutableLiveData<String?>()
    val navigationEvent: LiveData<String?> = _navigationEvent

    fun setUsername(newUsername: String) {
        _username.value = newUsername
        _signUpError.value = null
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
        _signUpError.value = null
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
        _signUpError.value = null
    }

    fun setConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _signUpError.value = null
    }

    fun signUp() {
        viewModelScope.launch {
            Log.d("SignUpViewModel", "Sign up process started")
            val emailValue = _email.value ?: ""
            val passwordValue = _password.value ?: ""
            val confirmPasswordValue = _confirmPassword.value ?: ""

            if (emailValue.isBlank() || passwordValue.isBlank() || confirmPasswordValue.isBlank()) {
                _signUpError.value = "All fields are required."
                return@launch
            }

            if (!isValidEmail(emailValue)) {
                _signUpError.value = "Invalid email format."
                return@launch
            }

            if (passwordValue != confirmPasswordValue) {
                _signUpError.value = "Passwords do not match."
                return@launch
            }

            if (passwordValue.length < 6) {
                _signUpError.value = "Password must be at least 6 characters long."
                return@launch
            }

            auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SignUpViewModel", "Firebase Auth account created successfully")
                        _signUpSuccess.value = true
                        _signUpError.value = null
                        _navigationEvent.value = "home"
                        Log.d("SignUpViewModel", "Navigation event set to home: ${_navigationEvent.value}")
                    } else {
                        Log.e("SignUpViewModel", "Sign up failed: ${task.exception?.message}")
                        _signUpSuccess.value = false
                        _signUpError.value = "Sign up failed: ${task.exception?.message}"
                    }
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}
