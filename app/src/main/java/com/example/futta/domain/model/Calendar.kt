package com.example.futta.domain.model

class Calendar(val events: List<CalendarEvent>)


fun addEvent(calendar: Calendar, event: CalendarEvent): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    mutableEvents.add(event)
    return Calendar(mutableEvents)
}

fun deleteEvent(calendar: Calendar, event: CalendarEvent): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    mutableEvents.removeIf { it.id == event.id }
    return Calendar(mutableEvents)
}


fun updateEvent(calendar: Calendar, event: CalendarEvent): Calendar {
    val mutableEvents = calendar.events.toMutableList()
    val eventIndex = mutableEvents.indexOfFirst {
        it.id == event.id
    }
    mutableEvents[eventIndex] = event
    return Calendar(mutableEvents)
}

