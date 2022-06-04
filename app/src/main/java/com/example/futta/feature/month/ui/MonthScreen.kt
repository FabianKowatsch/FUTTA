package com.example.futta.feature.month.ui

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.futta.feature.main.navigation.BottomNavigationItem
import java.time.LocalDate
import java.util.*

@Composable
fun MonthScreenUi(navController: NavController) {

    Row(
        modifier = Modifier
            .padding(6.dp),
        verticalAlignment = Alignment.Top
    ) {

        AndroidView(
            { CalendarView(it) },
            modifier = Modifier.wrapContentWidth(),
            update = { view ->
                view.date = Calendar.getInstance().timeInMillis
                view.firstDayOfWeek = 2
                view.setOnDateChangeListener { calendarView, y, m, d ->
                        val date: LocalDate = LocalDate.of(y, m+1, d)
                        println(date)
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
        )

        Text(
            text = "Today",
            modifier = Modifier
                .wrapContentWidth(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            color = Color(0xFF03B670),
            textAlign = TextAlign.Center,
        )

    }
}