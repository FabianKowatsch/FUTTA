package com.example.futta.data

import com.example.futta.domain.model.CalendarEvent
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.TimeSlot
import com.example.futta.domain.model.TimeSpan
import java.time.LocalTime

val eventRepo = CalendarEventRepository()
class CalendarEventRepository{
    private val allEvents: List<CalendarEvent> = listOfNotNull(
        CalendarEvent.Default.create(
            id = EventId("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"),
            title = "birthday max",
            description = "this is an event",
            timeSlot = TimeSlot.FULL_DAY
        ),
        CalendarEvent.Lecture.create(
            id = EventId("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
            title = "management",
            description = "this event shouldn't be attended",
            timeSlot = TimeSlot.ONE,
            location = "HFU I 0.012",
            roomUrl = "url.com",
            felixUrl = "url2.com"
        ),
        CalendarEvent.Lecture.create(
            id = EventId("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
            title = "economy",
            description = "this event shouldn't be attended aswell",
            timeSlot = TimeSlot.TWO,
            location = "HFU I 0.012",
            roomUrl = "url.com",
            felixUrl = "url2.com"
        )
    )

    suspend fun getAllEvents() = allEvents
    suspend fun getEventById(id: EventId): CalendarEvent? = getAllEvents().firstOrNull {
        it.id == id
    }
}