package com.example.futta.domain.model

import java.time.LocalTime

@JvmInline
value class EventId(val value: String)
sealed class CalendarEvent{
    abstract val id: EventId
    abstract val title: String
    abstract val description: String
    abstract val timeSpan: TimeSpan

    class Default private constructor(
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan
    ): CalendarEvent() {
        companion object {
            fun create(
                id: EventId,
                title: String,
                description: String,
                timeSlot: TimeSlot,
                timeSpan: TimeSpan = TimeSpan(LocalTime.of(0,0), LocalTime.of(23,59, 59))
            ): CalendarEvent {
                return Default(
                    id,
                    title,
                    description,
                    getCorrespondingTime(timeSlot, timeSpan)
                )
            }
        }
    }

    class Lecture private constructor(
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan,
        val location: String,
        val roomUrl: String,
        val felixUrl: String
    ): CalendarEvent() {
        companion object {
            fun create(
                id: EventId,
                title: String,
                description: String,
                timeSlot: TimeSlot,
                timeSpan: TimeSpan = TimeSpan(LocalTime.of(0,0), LocalTime.of(23,59, 59)),
                location: String,
                roomUrl: String,
                felixUrl: String

            ): CalendarEvent {
                return Lecture(
                    id,
                    title,
                    description,
                    getCorrespondingTime(timeSlot, timeSpan),
                    location,
                    roomUrl,
                    felixUrl
                )
            }
        }
    }
}

data class TimeSpan(val timeStart: LocalTime, val timeEnd: LocalTime)

enum class TimeSlot {
    ONE, TWO, THREE, FOUR, FIVE, SIX, FULL_DAY, CUSTOM
}
fun getCorrespondingTime(timeSlot: TimeSlot, timeSpan: TimeSpan): TimeSpan{
    return when (timeSlot){
        TimeSlot.ONE -> TimeSpan(LocalTime.of(8,0,), LocalTime.of(9,30))
        TimeSlot.TWO -> TimeSpan(LocalTime.of(9,45), LocalTime.of(11,15))
        TimeSlot.THREE -> TimeSpan(LocalTime.of(11,30), LocalTime.of(13,0))
        TimeSlot.FOUR -> TimeSpan(LocalTime.of(14,0), LocalTime.of(15,30))
        TimeSlot.FIVE -> TimeSpan(LocalTime.of(15,45), LocalTime.of(17,15))
        TimeSlot.SIX -> TimeSpan(LocalTime.of(17,30), LocalTime.of(19,0))
        TimeSlot.FULL_DAY -> TimeSpan(LocalTime.of(0,0,0), LocalTime.of(23,59))
        TimeSlot.CUSTOM -> timeSpan

    }
}