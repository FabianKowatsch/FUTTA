package com.example.futta.feature.month.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.futta.feature.main.navigation.BottomNavigationItem
import java.time.LocalDate
import com.example.futta.R
import com.prolificinteractive.materialcalendarview.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.Month

@Composable
fun MonthScreenUi(navController: NavController, viewModel: MonthViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
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
            view.setDateTextAppearance(R.style.calendar_text)
            val decorator = DayDecoratorImpl()
            coroutineScope.launch { decorator.setDayList(viewModel.getEventsOfMonth(LocalDate.now().month)) }

            view.addDecorator(DayDecoratorImpl())
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
            view.setOnMonthChangedListener { widget, date ->
                coroutineScope.launch {
                    decorator.setDayList(
                        viewModel.getEventsOfMonth(
                            Month.of(
                                date.month
                            )
                        )
                    )
                }
                widget.invalidateDecorators()
            }

        }


    }
}