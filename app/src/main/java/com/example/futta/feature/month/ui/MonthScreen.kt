package com.example.futta.feature.month.ui

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.futta.feature.main.navigation.BottomNavigationItem
import java.time.LocalDate
import com.example.futta.R
import com.prolificinteractive.materialcalendarview.*
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun MonthScreenUi(navController: NavController, viewModel: MonthViewModel = viewModel()) {
    val events by viewModel.bindUi().observeAsState(emptyList())
    val isDarkMode by viewModel.darkMode.observeAsState()
    val eventBackground = AppCompatResources.getDrawable(LocalContext.current, R.drawable.month_event_background)
    Row(
        modifier = Modifier
            .padding(6.dp),
        verticalAlignment = Alignment.Top
    ) {

        AndroidView(
            { MaterialCalendarView(it) },
            modifier = Modifier.wrapContentWidth()
        ) { view ->
            view.currentDate = CalendarDay.today()
            view.setTitleMonths(R.array.month_labels)
            view.setWeekDayLabels(R.array.weekday_labels)
            view.selectionColor = R.color.green_3000
            isDarkMode?.let { it ->
                if (it) {
                    view.setDateTextAppearance(R.style.calendar_text_night)
                    view.setHeaderTextAppearance(R.style.calendar_text_night)
                    view.setWeekDayTextAppearance(R.style.calendar_text_night)
                } else {
                    view.setDateTextAppearance(R.style.calendar_text)
                    view.setHeaderTextAppearance(R.style.calendar_text)
                    view.setWeekDayTextAppearance(R.style.calendar_text)
                }
            }
            view.addDecorator(DayDecoratorImpl(events, eventBackground))
            view.setOnDateChangedListener { _, day, _ ->
                val date: LocalDate = LocalDate.of(day.year, day.month, day.day)
                navController.navigate(BottomNavigationItem.Day.createRoute((date.toString()))) {
                    navController.graph.startDestinationRoute?.let { screen_route ->
                        popUpTo(screen_route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }
        }
    }
}