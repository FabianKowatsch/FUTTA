package com.example.futta.feature.addEvent.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.futta.domain.model.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun UpdateEventScreen(viewModel: UpdateEventViewModel = viewModel(), eventId: EventId, navController: NavController) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState()
        event?.let { UpdateEventScreenUi(it, viewModel::onUpdateEvent, navController) }
}