package com.example.futta.feature.day.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarEventTeaserItem(event: CalendarEventTeaserUI, navController: NavController) {
    ListItem(
        text = {
            Text(
                text = event.title,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        },
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier
                        .padding(2.5.dp),
                    text = event.timeSpan.timeStart.truncatedTo(ChronoUnit.MINUTES).toString(),
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(2.5.dp, 15.dp, 2.5.dp, 2.5.dp),
                    text = event.timeSpan.timeEnd.truncatedTo(ChronoUnit.MINUTES).toString(),
                    fontSize = 10.sp,
                )
            }
        },
        trailing = {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "SwipeLeft"
            )
        },
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
    )
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