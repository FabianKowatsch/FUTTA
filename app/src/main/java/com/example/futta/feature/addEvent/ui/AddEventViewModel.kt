package com.example.futta.feature.addEvent.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futta.domain.AddEventUseCase
import com.example.futta.domain.model.CalendarEvent
import kotlinx.coroutines.launch

class AddEventViewModel: ViewModel() {
    fun onAddEvent(event: CalendarEvent) {
        viewModelScope.launch {
            AddEventUseCase()(event)
        }
    }
}