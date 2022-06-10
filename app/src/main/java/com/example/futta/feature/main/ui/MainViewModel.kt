package com.example.futta.feature.main.ui

import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.futta.domain.ObserveEventsUseCase
import com.example.futta.domain.model.CalendarEvent

class MainViewModel : ViewModel() {
    val currentRoute = MutableLiveData<String>("")
    fun bindUi(): LiveData<List<CalendarEvent>> = ObserveEventsUseCase()().asLiveData()
}