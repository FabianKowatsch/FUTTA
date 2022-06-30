package com.example.futta.data

import com.example.futta.App
import com.example.futta.data.database.CalendarEventDao
import com.example.futta.data.database.calendarEventFromDb
import com.example.futta.data.database.calendarEventToDb
import com.example.futta.domain.model.*
import kotlinx.coroutines.flow.*
import java.time.LocalDate

val calendarEventRepo = CalendarEventRepository(App.database.calendarEventDao())

class CalendarEventRepository(private val dao: CalendarEventDao) {

    suspend fun getAllEvents(): List<CalendarEvent> =
        dao.getAll().mapNotNull { calendarEventFromDb(it) }

    //fun observeAllEvents(): Flow<List<CalendarEvent>> = flowOf(allEvents)
    suspend fun getEventById(id: EventId): CalendarEvent? =
        dao.getById(id.value)?.let { calendarEventFromDb(it) }

    suspend fun addEvent(event: CalendarEvent) {
        dao.insert(calendarEventToDb(event))
    }

    suspend fun updateEvent(event: CalendarEvent) {
        dao.update(calendarEventToDb(event))
    }

    suspend fun deleteEvent(id: EventId) {
        dao.deleteById(id.value)
    }
}