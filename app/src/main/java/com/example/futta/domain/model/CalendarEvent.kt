package com.example.futta.domain.model

import java.time.LocalDate
import java.time.LocalTime

@JvmInline
value class EventId(val value: String)
sealed class CalendarEvent{
    abstract val id: EventId
    abstract val title: String
    abstract val description: String
    abstract val timeSpan: TimeSpan
    abstract val date: LocalDate
    abstract val cycleType: CycleType
    abstract val cancelOnHolidays: Boolean

    class Default private constructor(
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan,
        override val date: LocalDate,
        override val cycleType: CycleType,
        override val cancelOnHolidays: Boolean
    ): CalendarEvent() {
        companion object {
            fun create(
                id: EventId,
                title: String,
                description: String,
                timeSlot: TimeSlot,
                timeSpan: TimeSpan = TimeSpan(LocalTime.of(0,0), LocalTime.of(23,59, 59)),
                date: LocalDate,
                cycleType: CycleType,
                cancelOnHolidays: Boolean = false
            ): CalendarEvent {
                return Default(
                    id,
                    title,
                    description,
                    getCorrespondingTime(timeSlot, timeSpan),
                    date,
                    cycleType,
                    cancelOnHolidays
                )
            }
        }
    }

    class Lecture private constructor(
        override val id: EventId,
        override val title: String,
        override val description: String,
        override val timeSpan: TimeSpan,
        override val date: LocalDate,
        override val cycleType: CycleType,
        override val cancelOnHolidays: Boolean,
        val lectureInfo: LectureInfo
    ): CalendarEvent() {
        companion object {
            fun create(
                id: EventId,
                title: String,
                description: String,
                timeSlot: TimeSlot,
                timeSpan: TimeSpan = TimeSpan(LocalTime.of(0,0), LocalTime.of(23,59, 59)),
                date: LocalDate,
                cycleType: CycleType = CycleType.WEEK,
                lectureInfo: LectureInfo

            ): CalendarEvent {
                return Lecture(
                    id,
                    title,
                    description,
                    getCorrespondingTime(timeSlot, timeSpan),
                    date,
                    cycleType,
                    cancelOnHolidays = true,
                    lectureInfo
                )
            }
        }
    }
}

sealed class LectureInfo {
    object Unknown: LectureInfo()
    class Local(
        val felixUrl: String,
        val location: String
    ): LectureInfo()
    class Online(
        val felixUrl: String,
        val onlineUrl: String
    ): LectureInfo()
    class Hybrid(
        val felixUrl: String,
        val location: String,
        val onlineUrl: String
    ): LectureInfo()
}

data class TimeSpan(val timeStart: LocalTime, val timeEnd: LocalTime)

enum class CycleType {
    NONE, WEEK, MONTH, YEAR
}

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