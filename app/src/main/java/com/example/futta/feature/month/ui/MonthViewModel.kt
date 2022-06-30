package com.example.futta.feature.month.ui

import androidx.lifecycle.*
import com.example.futta.App
import com.example.futta.domain.GetEventsUseCase
import com.example.futta.domain.model.CycleType
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
    fun bindUi(): LiveData<List<MonthCalendarEventUI>> = liveData {
        val result = GetEventsUseCase()().map { calendarEvent ->
            MonthCalendarEventUI(
                date = calendarEvent.date,
                cycleType = calendarEvent.cycleType
            )
        }.sortedBy { it.date }
        emit(result)
    }
}
fun eventIsInMonth(eventDate: LocalDate, selectedMonth: Month, cycleType: CycleType): Boolean {
    return when(cycleType) {
        CycleType.YEAR -> eventDate.month == selectedMonth
        else -> true
    }
}
fun getDateInMonth(date: LocalDate, selectedMonth: Month): LocalDate = LocalDate.of(date.year, selectedMonth, date.dayOfMonth)