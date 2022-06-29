package com.example.futta.feature.main.ui

import androidx.lifecycle.*
import com.example.futta.App
import com.example.futta.domain.ObserveEventsUseCase
import com.example.futta.domain.model.CalendarEvent
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    val currentRoute = MutableLiveData<String>("")
    val darkMode = App.settingsRepo.isDarkMode().asLiveData()

    fun toggleDarkMode() {
        viewModelScope.launch { App.settingsRepo.toggleDarkMode()} }

    }
