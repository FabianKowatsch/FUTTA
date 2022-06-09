package com.example.futta.feature.addEvent.ui

import android.content.Context
import androidx.lifecycle.*
import com.example.futta.domain.AddEventUseCase
import com.example.futta.domain.GetEventByIdUseCase
import com.example.futta.domain.model.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class AddEventViewModel : ViewModel() {

    fun bindUi(context: Context): LiveData<UpdateCalendarEventUI> = liveData {
        val result = UpdateCalendarEventUI(
            id = EventId(UUID.randomUUID().toString()),
            title = "Title",
            description = "Description",
            timeSpan = TimeSpan(
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
            ),
            timeSlot = TimeSlot.CUSTOM,
            cycleType = CycleType.WEEK,
            cancelOnHolidays = true,
            date = LocalDate.now(),
            lectureInfo = LectureInfo.Hybrid("felixUrl", "location", "onlineUrl")
        )
        emit(result)
    }

    fun onAddEvent(event: UpdateCalendarEventUI) {
        viewModelScope.launch {
            val calendarEvent = when (event.lectureInfo) {
                is LectureInfo.Unknown -> CalendarEvent.Default.create(
                    id = event.id,
                    title = event.title,
                    description = event.description,
                    date = event.date,
                    timeSpan = event.timeSpan,
                    cycleType = event.cycleType,
                    cancelOnHolidays = event.cancelOnHolidays,
                    timeSlot = event.timeSlot
                )
                else -> CalendarEvent.Lecture.create(
                    id = event.id,
                    title = event.title,
                    description = event.description,
                    date = event.date,
                    timeSpan = event.timeSpan,
                    cycleType = event.cycleType,
                    timeSlot = event.timeSlot,
                    lectureInfo = event.lectureInfo
                )
            }
            AddEventUseCase()(calendarEvent)
        }
    }
}


