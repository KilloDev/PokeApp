package com.skynoff.pokeapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skynoff.pokeapp.data.local.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState())
    val loginState = _loginState.asStateFlow()


    fun login(user: String, password: String) {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true, errorMessage = null)
            authManager.validateLogin(user, password).collect { isValid ->
                if (isValid){
                    authManager.saveLogin(user)
                    _loginState.value = LoginState(isValidLogin = true)
                } else {
                    _loginState.value = LoginState(
                        isValidLogin = false,
                        errorMessage = "Usuario o contraseña incorrectos"
                    )
                }

            }

        }
    }
    fun clearError() {
        _loginState.value = _loginState.value.copy(errorMessage = null)
    }
}

data class LoginState(
    val isValidLogin: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)