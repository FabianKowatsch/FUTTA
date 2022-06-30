package com.example.futta

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.futta.data.SettingsRepository
import com.example.futta.data.database.AppDatabase
import com.example.futta.domain.AddDemoEventsUseCase
import kotlinx.coroutines.*

/**
 * Main entry point into the application process.
 * Registered in the AndroidManifest.xml file.
 */
class App : Application() {
    private val settingsStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        settingsRepo = SettingsRepository(settingsStore)
        database = Room
            .databaseBuilder(this, AppDatabase::class.java, "app")
            .apply {
                if (BuildConfig.DEBUG) fallbackToDestructiveMigration()
            }
            .build()
        scope.launch {
            AddDemoEventsUseCase()()
        }

    }

    companion object {
        lateinit var settingsRepo: SettingsRepository
        lateinit var database: AppDatabase
    }
}