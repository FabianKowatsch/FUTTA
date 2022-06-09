package com.example.futta.feature.addEvent.ui

import com.example.futta.domain.model.*
import java.time.LocalDate

data class UpdateCalendarEventUI(
    val id: EventId,
    val title: String,
    val description: String,
    val timeSpan: TimeSpan,
    val timeSlot: TimeSlot,
    val date: LocalDate,
    val cycleType: CycleType,
    val cancelOnHolidays: Boolean,
    val lectureInfo: LectureInfo,
)