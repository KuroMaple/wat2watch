package com.team1.wat2watch.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    private val _forgotPasswordEvent = MutableLiveData<Unit>()
    val forgotPassword: LiveData<Unit> = _forgotPasswordEvent
    private val _createAccountEvent = MutableLiveData<Unit>()
    val createAccount: LiveData<Unit> = _createAccountEvent
    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> = _loginError

    private val _forgotPasswordSuccess = MutableLiveData<Boolean?>()
    val forgotPasswordSuccess: MutableLiveData<Boolean?> = _forgotPasswordSuccess

    fun setEmail(newEmail: String) {
        _email.value = newEmail
        _loginError.value = null  // Clear error when email is changed
        _forgotPasswordSuccess.value = null
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
        _loginError.value = null  // Clear error when password is changed
        _forgotPasswordSuccess.value = null
    }

    fun login() {
        val emailValue = _email.value ?: ""
        val passwordValue = _password.value ?: ""

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _loginError.value = "Email and password cannot be empty."
            return
        }

        if (!isValidEmail(emailValue)) {
            _loginError.value = "Email not correctly formatted"
            return
        }

        attemptSignIn(emailValue, passwordValue)
    }

    private fun attemptSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginSuccess.value = true
                    _loginError.value = null
                } else {
                    _loginSuccess.value = false
                    when (task.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            _loginError.value = "Incorrect password"
                        }
                        is FirebaseAuthInvalidUserException -> {
                            _loginError.value = "No account found with this email"
                        }
                        else -> {
                            _loginError.value = "Login failed: ${task.exception?.message}"
                        }
                    }
                    Log.e("FirebaseAuth", "Error signing in: ${task.exception?.message}")
                }
            }
    }

    fun onForgotPasswordClick() {
        val emailValue = _email.value ?: ""
        if (emailValue.isBlank()) {
            _loginError.value = "Please enter your email address to reset password."
            return
        }
        if (!isValidEmail(emailValue)) {
            _loginError.value = "Please enter a valid email address."
            return
        }

        auth.sendPasswordResetEmail(emailValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _forgotPasswordSuccess.value = true
                    _loginError.value = null
                } else {
                    _forgotPasswordSuccess.value = false
                    _loginError.value = "Failed to send reset email: ${task.exception?.message}"
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onCreateAccountClick() {
        _createAccountEvent.value = Unit
    }
}
