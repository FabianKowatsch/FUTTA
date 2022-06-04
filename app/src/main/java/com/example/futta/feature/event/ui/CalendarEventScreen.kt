package com.example.futta.feature.event.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.futta.domain.model.EventId

@Composable
fun CalendarEventScreen(viewModel: CalendarEventViewModel = viewModel(), eventId: EventId) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState()
    CalendarEventScreenUi(event = event)

}

@Composable
fun CalendarEventScreenUi(event: CalendarEventUI?) {

}