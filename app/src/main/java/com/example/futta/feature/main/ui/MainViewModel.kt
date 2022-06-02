package com.example.futta.feature.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.futta.domain.ObserveEventsUseCase
import com.example.futta.domain.model.CalendarEvent

class MainViewModel : ViewModel() {
    fun bindUi(): LiveData<List<CalendarEvent>> = ObserveEventsUseCase()().asLiveData()
}