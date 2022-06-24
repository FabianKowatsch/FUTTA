package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import com.example.futta.domain.model.CalendarEvent
import com.example.futta.domain.model.EventId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteEventUseCase {
    suspend operator fun invoke(eventId: EventId) = withContext(Dispatchers.Default) { calendarEventRepo.deleteEvent(eventId) }
}