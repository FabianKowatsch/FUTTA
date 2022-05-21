package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetEventsUseCase {
    suspend operator fun invoke() = withContext(Dispatchers.Default) { calendarEventRepo.getAllEvents() }
}