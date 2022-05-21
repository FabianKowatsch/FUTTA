package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import com.example.futta.domain.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddEventUseCase {
    suspend operator fun invoke(event: CalendarEvent) = withContext(Dispatchers.Default) { calendarEventRepo.addEvent(event) }
}