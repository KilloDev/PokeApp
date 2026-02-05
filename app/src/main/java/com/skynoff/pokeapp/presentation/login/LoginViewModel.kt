package com.skynoff.pokeapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skynoff.pokeapp.data.local.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    val isLoggedIn = authManager.isLoggedIn.asLiveData()

    fun login(user: String) {
        viewModelScope.launch {
            authManager.saveLogin(user)
        }
    }
}