package com.example.futta.feature.month.ui

import android.content.Context
import androidx.lifecycle.*
import com.example.futta.App
import com.example.futta.domain.GetEventsUseCase
import com.example.futta.domain.ObserveEventsUseCase
import com.example.futta.domain.model.CalendarEvent
import com.example.futta.domain.model.CycleType
import com.example.futta.feature.day.ui.CalendarEventTeaserUI
import com.example.futta.feature.day.ui.eventIsOnDate
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class MonthViewModel() : ViewModel() {
    val darkMode = App.settingsRepo.isDarkMode().asLiveData()
    suspend fun getEventsOfMonth(selectedMonth: Month):List<LocalDate>  {
        val matchingEvents = GetEventsUseCase()().filter { event -> eventIsInMonth(event.date, selectedMonth, event.cycleType) }
        val result = matchingEvents.map { calendarEvent ->
        getDateInMonth(calendarEvent.date, selectedMonth)
        }.sortedBy { it.dayOfMonth}
       return result
    }
}
fun eventIsInMonth(eventDate: LocalDate, selectedMonth: Month, cycleType: CycleType): Boolean {
    return when(cycleType) {
        CycleType.YEAR -> eventDate.month == selectedMonth
        else -> true
    }
}
fun getDateInMonth(date: LocalDate, selectedMonth: Month): LocalDate = LocalDate.of(date.year, selectedMonth, date.dayOfMonth)