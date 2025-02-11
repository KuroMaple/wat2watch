package com.team1.wat2watch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel {
    private val _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    fun setCode(newCode: String) {
        _code.value = newCode
    }
}