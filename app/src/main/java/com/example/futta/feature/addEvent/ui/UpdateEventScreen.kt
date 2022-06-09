package com.example.futta.feature.addEvent.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.futta.domain.model.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun UpdateEventScreen(viewModel: UpdateEventViewModel = viewModel(), eventId: EventId) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState(
        UpdateCalendarEventUI(
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
    )
        event?.let { UpdateEventScreenUi(it, viewModel::onUpdateEvent) }
}