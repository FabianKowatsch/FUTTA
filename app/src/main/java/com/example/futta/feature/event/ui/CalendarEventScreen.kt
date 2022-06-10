package com.example.futta.feature.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.futta.R
import com.example.futta.domain.model.EventId
import com.example.futta.domain.model.LectureInfo
import com.example.futta.domain.model.TimeSpan
import com.example.futta.feature.main.navigation.BottomNavigationItem
import com.example.futta.feature.main.navigation.NavigationItem
import java.time.temporal.ChronoUnit

@Composable
fun CalendarEventScreen(viewModel: CalendarEventViewModel = viewModel(), eventId: EventId, navController: NavController) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState()
    CalendarEventScreenUi(event = event, navController)

}

@Composable
fun CalendarEventScreenUi(event: CalendarEventUI?, navController: NavController) {
    if (event != null) {
        val dateString = event.date.toString()
        Scaffold(
                topBar = {
                    Column(modifier = Modifier
                            .background(colorResource(id = R.color.green_3100))
                            .fillMaxWidth()
                            .padding(10.dp, 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                                text = dateString,
                                color = colorResource(id = R.color.white)
                        )
                    }
                },
            floatingActionButton = {
                FloatingActionButton(
                    content = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = "") },
                    onClick = {
                        navController.navigate(NavigationItem.UpdateEvent.createRoute(event.id)) {
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
            }
        ) {
            Column(

            ) {
                Text(
                        modifier = Modifier
                                .padding(2.5.dp),
                        text = event.timeSpan.timeStart.truncatedTo(ChronoUnit.MINUTES).toString(),
                        fontSize = 12.sp,
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
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                )
            }
            Column(

            ) {
                Text(
                        modifier = Modifier
                                .padding(2.5.dp, 15.dp, 2.5.dp, 2.5.dp),
                        text = event.timeSpan.timeEnd.truncatedTo(ChronoUnit.MINUTES).toString(),
                        fontSize = 12.sp,
                )
            }
            Column(
            ) {
                Text(
                        modifier = Modifier
                                .padding(50.dp, 35.dp, 5.dp, 5.dp),
                        text = event.description,
                        fontSize = 12.sp,
                )
            }
        }
    }
}
