package com.example.futta.feature.day.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.TimeSpan
import com.example.futta.feature.main.navigation.NavigationItem
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun CalendarEventTeaserItem(event: CalendarEventTeaserUI, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    navController.navigate(NavigationItem.Event.createRoute(event.id)) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            )
    ) {
        Column(

        ) {
            Text(
                modifier = Modifier
                    .padding(2.5.dp),
                text = event.timeSpan.timeStart.truncatedTo(ChronoUnit.MINUTES).toString(),
                fontSize = 10.sp,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = event.title,
                //  color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,

                )
        }
        Column(

        ) {
            Text(
                modifier = Modifier
                    .padding(2.5.dp, 15.dp, 2.5.dp, 2.5.dp),
                text = event.timeSpan.timeEnd.truncatedTo(ChronoUnit.MINUTES).toString(),
                fontSize = 10.sp,
            )
        }
    }
}


@Preview
@Composable
fun CalendarEventTeaserItem_Preview() {
    CalendarEventTeaserItem(
        event = CalendarEventTeaserUI(
            EventId("sdgfsfgd"),
            "Beispielevent",
            TimeSpan(LocalTime.now(), LocalTime.now())
        ), navController = rememberNavController()
    )
}