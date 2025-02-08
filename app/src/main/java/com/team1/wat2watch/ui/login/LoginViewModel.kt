package com.team1.wat2watch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
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

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        val emailValue = _email.value ?: ""
        val passwordValue = _password.value ?: ""

        // TODO: Replace with Firebase Authentication
        /*
        val isAuthenticated = authenticateWithServer(emailValue, passwordValue)
        */
        // Currently mock authentication
        _loginSuccess.value = emailValue == "test@uwaterloo.ca" && passwordValue == "password123"

    }

    fun forgotPassword() {
        _forgotPasswordEvent.value = Unit
    }

    fun createAccount() {
        _createAccountEvent.value = Unit
    }

}