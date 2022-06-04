package com.example.futta.feature.day.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.futta.feature.main.navigation.BottomNavigationItem
import java.time.LocalDate

@Composable
fun DayScreen(viewModel: DayViewModel = viewModel(), date: LocalDate) {
    val events by viewModel.bindUi(LocalContext.current, date).observeAsState(emptyList())
    DayScreenUi(date = date, events = events)
}

@Composable
fun DayScreenUi(date: LocalDate, events: List<CalendarEventTeaserUI>) {
    val dateString = date.toString()
    Scaffold(
            topBar = {
                TopAppBar(
                        title = {
                            Text(dateString)
                        }
                )
            },
    ) {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 2.dp, bottom = 2.dp)
                        .background(Color(0xFF777777), shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),

                ) {

            if (events.isNotEmpty()) {
                events.forEach { event ->
                    CalendarEventTeaserItem(event)

                }

            } else {
                Text(
                        text = "Für diesen Tag sind bisher keine Veranstaltungen geplant",
                        color = Color(0xFFFFFFFF),
                        overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
