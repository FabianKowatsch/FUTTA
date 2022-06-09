package com.example.futta.feature.addEvent.ui

import android.content.Context
import androidx.lifecycle.*
import com.example.futta.domain.GetEventByIdUseCase
import com.example.futta.domain.UpdateEventUseCase
import com.example.futta.domain.model.*
import kotlinx.coroutines.launch

class UpdateEventViewModel : ViewModel() {
    fun bindUi(context: Context, eventId: EventId): LiveData<UpdateCalendarEventUI?> = liveData {
        val event = GetEventByIdUseCase()(eventId)
        if (event == null) emit(null)
        else {
            val lectureInfo = when (event) {
                is CalendarEvent.Default -> LectureInfo.Unknown
                is CalendarEvent.Lecture -> event.lectureInfo
            }
            val result = UpdateCalendarEventUI(
                id = event.id,
                title = event.title,
                description = event.description,
                timeSpan = event.timeSpan,
                cycleType = event.cycleType,
                lectureInfo = lectureInfo,
                date = event.date,
                cancelOnHolidays = event.cancelOnHolidays,
                timeSlot = TimeSlot.CUSTOM
            )
            emit(result)
        }
    }

    fun onUpdateEvent(event: UpdateCalendarEventUI) {
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
            UpdateEventUseCase()(calendarEvent)

        }
    }
}