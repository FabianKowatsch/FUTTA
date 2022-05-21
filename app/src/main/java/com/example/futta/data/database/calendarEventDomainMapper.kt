package com.example.futta.data.database

import com.example.futta.domain.model.*

fun calendarEventToDb(event: CalendarEvent): CalendarEventDb = CalendarEventDb(
        id = event.id.value,
        title = event.title,
        description = event.description,
        timeStart = event.timeSpan.timeStart,
        timeEnd = event.timeSpan.timeEnd,
        date = event.date,
        cycleType = event.cycleType,
        cancelOnHolidays = event.cancelOnHolidays,
        felixUrl = if(event is CalendarEvent.Lecture) LectureInfo.getValuesBasedOnType(event.lectureInfo)["felixUrl"] else null,
        onlineUrl = if(event is CalendarEvent.Lecture) LectureInfo.getValuesBasedOnType(event.lectureInfo)["onlineUrl"] else null,
        location = if(event is CalendarEvent.Lecture) LectureInfo.getValuesBasedOnType(event.lectureInfo)["location"] else null,
    )


fun calendarEventFromDb(event: CalendarEventDb): CalendarEvent {
    val lectureInfo: LectureInfo = LectureInfo.createBasedOnValues(felixUrl = event.felixUrl, location = event.location, onlineUrl = event.onlineUrl)
    return when (lectureInfo) {
        is LectureInfo.Unknown -> CalendarEvent.Default.create(
            id = EventId(event.id),
            title = event.title,
            description = event.description,
            timeSlot = TimeSlot.CUSTOM,
            timeSpan = TimeSpan(event.timeStart, event.timeEnd),
            date = event.date,
            cycleType = event.cycleType,
            cancelOnHolidays = event.cancelOnHolidays,
        )
        else -> CalendarEvent.Lecture.create(
            id = EventId(event.id),
            title = event.title,
            description = event.description,
            timeSlot = TimeSlot.CUSTOM,
            timeSpan = TimeSpan(event.timeStart, event.timeEnd),
            date = event.date,
            cycleType = event.cycleType,
            lectureInfo = lectureInfo
        )
    }
}