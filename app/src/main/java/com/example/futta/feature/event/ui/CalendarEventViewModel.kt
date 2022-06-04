package com.example.futta.feature.event.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.futta.domain.GetEventByIdUseCase
import com.example.futta.domain.model.CalendarEvent
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.LectureInfo

class CalendarEventViewModel : ViewModel() {
    fun bindUi(context: Context, eventId: EventId): LiveData<CalendarEventUI?> = liveData {
        val event = GetEventByIdUseCase()(eventId)
        if (event == null) emit(null)
        else {
            val lectureInfo = when (event) {
                is CalendarEvent.Default -> LectureInfo.Unknown
                is CalendarEvent.Lecture -> event.lectureInfo
            }
            val result = CalendarEventUI(
                id = event.id,
                title = event.title,
                description = event.description,
                timeSpan = event.timeSpan,
                cycleType = event.cycleType,
                lectureInfo = lectureInfo
            )
            emit(result)
        }
    }
}