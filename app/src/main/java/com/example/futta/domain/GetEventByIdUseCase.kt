package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import com.example.futta.domain.model.EventId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetEventByIdUseCase {
    suspend operator fun invoke(id: EventId) = withContext(Dispatchers.Default) { calendarEventRepo.getEventById(id) }
}