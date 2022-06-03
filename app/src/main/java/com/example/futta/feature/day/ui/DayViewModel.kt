package com.example.futta.feature.day.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.futta.domain.GetEventsUseCase
import com.example.futta.domain.model.CycleType
import kotlinx.coroutines.launch
import java.time.LocalDate

class DayViewModel : ViewModel() {
    fun bindUi(context: Context, selectedDate: LocalDate): LiveData<List<CalendarEventTeaserUI>> = liveData {
        val matchingEvents = GetEventsUseCase()().filter { event -> eventIsOnDate(event.date, selectedDate, event.cycleType) }
        val result = matchingEvents.map { calendarEvent ->
            CalendarEventTeaserUI(
                id = calendarEvent.id,
                title = calendarEvent.title,
                timeSpan = calendarEvent.timeSpan
            )
        }.sortedBy { it.timeSpan.timeStart }
        emit(result)
    }
}

fun eventIsOnDate(eventDate: LocalDate, selectedDate: LocalDate, cycleType: CycleType): Boolean {
    return when(cycleType) {
        CycleType.YEAR -> eventDate.month == selectedDate.month && eventDate.dayOfMonth == selectedDate.dayOfMonth
        CycleType.MONTH -> eventDate.dayOfMonth == selectedDate.dayOfMonth
        CycleType.WEEK -> eventDate.dayOfWeek == selectedDate.dayOfWeek
        CycleType.NONE -> eventDate.equals(selectedDate)
    }
}