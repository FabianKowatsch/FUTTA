package com.example.futta.feature.month.ui

import com.example.futta.domain.model.CycleType
import java.time.LocalDate

data class MonthCalendarEventUI(
    val date: LocalDate,
    val cycleType: CycleType
)