package com.example.futta.domain

import com.example.futta.data.calendarEventRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ObserveEventsUseCase {
    operator fun invoke() = calendarEventRepo.observeAllEvents().flowOn(Dispatchers.Default)
}