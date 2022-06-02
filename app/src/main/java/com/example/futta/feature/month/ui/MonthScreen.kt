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
                view.setOnDateChangeListener { calendarView, y, m, d ->

                        println(LocalDate.of(y, m, d))
                        navController.navigate("day") {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
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
            color = Color(0xFF0A70C4),
            textAlign = TextAlign.Center,
        )

    }
}