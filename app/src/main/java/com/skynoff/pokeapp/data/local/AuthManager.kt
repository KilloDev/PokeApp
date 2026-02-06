package com.skynoff.pokeapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AuthManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveLogin(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
            prefs[IS_LOGGED_IN] = true
        }
    }

    suspend fun validateLogin(user: String, password: String): Flow<Boolean> {
        return if (user == "ashketchum" && password == "123456"){
            flowOf(true)
        } else {
             flowOf(false)
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[IS_LOGGED_IN] ?: false }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = false
            prefs[USER_NAME] = ""
        }
    }
}