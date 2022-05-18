package com.example.futta.domain.model

import java.time.LocalDate

class EventWithDates(
    val event: CalendarEvent,
    val dates: List<LocalDate>
)
