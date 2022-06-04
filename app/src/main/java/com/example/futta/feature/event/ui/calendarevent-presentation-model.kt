package com.example.futta.feature.event.ui

import com.example.futta.domain.model.CycleType
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.LectureInfo
import com.example.futta.domain.model.TimeSpan

class CalendarEventUI(
    val id: EventId,
    val title: String,
    val description: String,
    val timeSpan: TimeSpan,
    val cycleType: CycleType,
    val lectureInfo: LectureInfo,
)