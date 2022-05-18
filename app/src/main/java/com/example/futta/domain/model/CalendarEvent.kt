package com.example.futta.domain.model

import java.time.LocalTime
@JvmInline
value class EventId(val value: String)
sealed class CalendarEvent{
    abstract val id: EventId
    abstract val title: String
    abstract val description: String
    abstract val timeSpan: TimeSpan

    class Default (
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan
    ): CalendarEvent()
    class Lecture (
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan,
        val location: String,
        val roomUrl: String,
        val felixUrl: String
    ): CalendarEvent()
}

data class TimeSpan(val timeStart: LocalTime, val timeEnd: LocalTime)
