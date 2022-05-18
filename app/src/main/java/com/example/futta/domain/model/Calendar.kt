package com.example.futta.domain.model

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate

class Calendar(val events: List<EventWithDates>)

data class EventWithDates(
    val event: CalendarEvent,
    val dates: List<LocalDate>
)

fun addEventWithDates(calendar: Calendar, event: EventWithDates): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    mutableEvents.add(event)
    return Calendar(mutableEvents)
}

fun removeEventWithDates(calendar: Calendar, event: EventWithDates): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    mutableEvents.removeIf { it.event.id == event.event.id }
    return Calendar(mutableEvents)
}

fun removeDateFromEvent(calendar: Calendar, event: EventWithDates, date: LocalDate): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    val eventIndex = mutableEvents.indexOfFirst {
        it.event.id == event.event.id
    }
    val mutableDateList = event.dates.toMutableList()
    mutableDateList.removeIf {
        it.isEqual(ChronoLocalDate.from(date))
    }
    val updatedEvent: EventWithDates = event.copy(dates = mutableDateList)
    return Calendar(mutableEvents)
}

fun updateEvent(calendar: Calendar, oldEvent: EventWithDates, newEvent: CalendarEvent): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    val eventIndex = mutableEvents.indexOfFirst {
        it.event.id == oldEvent.event.id
    }
    val updatedEvent: EventWithDates = oldEvent.copy( event = newEvent, dates = oldEvent.dates)
    mutableEvents[eventIndex] = updatedEvent
    return Calendar(mutableEvents)
}

