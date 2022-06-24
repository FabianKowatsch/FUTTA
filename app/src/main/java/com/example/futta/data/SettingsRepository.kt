package com.example.futta.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    fun isDarkMode() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[KEY_DARKMODE] ?: true }

    suspend fun toggleDarkMode() {
        dataStore.edit {
            it[KEY_DARKMODE] = !(it[KEY_DARKMODE] ?: true)
        }
    }


    private companion object {
        private val KEY_DARKMODE = booleanPreferencesKey("darkMode")
    }
}
