package com.example.futta.data

import com.example.futta.domain.model.*
import kotlinx.coroutines.flow.*
import java.time.LocalDate

val calendarEventRepo = CalendarEventRepository()

class CalendarEventRepository {

    private val allEvents: MutableList<CalendarEvent> = listOfNotNull(
        CalendarEvent.Default.create(
            id = EventId("6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"),
            title = "birthday max",
            description = "this is an event",
            timeSlot = TimeSlot.FULL_DAY ,
            date = LocalDate.now(),
            cycleType = CycleType.NONE
        ),
        CalendarEvent.Lecture.create(
            id = EventId("d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
            title = "management",
            description = "this event shouldn't be attended",
            timeSlot = TimeSlot.TWO,
            date = LocalDate.now(),
            lectureInfo = LectureInfo.Hybrid(
                location = "HFU I 0.012",
                onlineUrl = "url.com",
                felixUrl = "url2.com",)
        ),
        CalendarEvent.Lecture.create(
            id = EventId("f4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"),
            title = "economy",
            description = "this event shouldn't be attended as well",
            timeSlot = TimeSlot.THREE,
            date = LocalDate.now(),
            lectureInfo = LectureInfo.Online(
                onlineUrl = "url.com",
                felixUrl = "url2.com",)
        )
    ).toMutableList()

    suspend fun getAllEvents(): List<CalendarEvent> = allEvents
    fun observeAllEvents(): Flow<List<CalendarEvent>> = flowOf(allEvents)
    suspend fun getEventById(id: EventId): CalendarEvent? = allEvents.firstOrNull {
        it.id == id
    }
    suspend fun addEvent(event: CalendarEvent) {
        allEvents.add(event)
    }
    suspend fun updateEvent(event: CalendarEvent) {
       val eventIndex = allEvents.indexOfFirst {
            it.id == event.id
        }
        allEvents[eventIndex] = event
    }
    suspend fun deleteEvent(event: CalendarEvent) {
        allEvents.remove(event)
    }
}