package com.example.finalprojectirmameza

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferences")

class DataStoreManager(private val context: Context) {
    companion object {
        private val PASSWORD_KEY = stringPreferencesKey("user_password")

    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit {
            prefs -> prefs[PASSWORD_KEY] = password
        }
    }
    val getPassword: Flow<String?> = context.dataStore.data.map {
        prefs -> prefs[PASSWORD_KEY]
    }
    suspend fun clearPassword() {
        context.dataStore.edit { prefs ->
            prefs.remove(PASSWORD_KEY)
        }
    }
}
