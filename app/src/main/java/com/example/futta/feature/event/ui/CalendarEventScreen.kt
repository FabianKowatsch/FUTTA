package com.example.futta.feature.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
fun CalendarEventScreen(
    viewModel: CalendarEventViewModel = viewModel(),
    eventId: EventId,
    navController: NavController
) {
    val event by viewModel.bindUi(LocalContext.current, eventId = eventId).observeAsState()
    CalendarEventScreenUi(event = event, navController)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarEventScreenUi(event: CalendarEventUI?, navController: NavController) {
    if (event != null) {
        val dateString = event.date.toString()
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .background(colorResource(id = R.color.green_3100))
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dateString, style = MaterialTheme.typography.caption,
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
                    },
                    backgroundColor = MaterialTheme.colors.secondaryVariant
                )
            }
        ) {
            LazyColumn() {
                items(count = 1) {
                    ListItem() {
                        Text(
                            event.title,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Divider()
                    ListItem(icon = {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = ""
                        )
                    }) {
                        Text(event.description)
                    }
                    Divider()
                    ListItem(icon = {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = ""
                        )
                    }) {
                        Text(event.timeSpan.timeStart.toString() + " - " + event.timeSpan.timeEnd.toString())
                    }

                    Divider()
                    event.lectureInfo?.let {
                        val info = LectureInfo.getValuesBasedOnType(it)
                        info["felixUrl"]?.let {
                            ListItem(icon = {          Icon(
                                imageVector = Icons.Outlined.Language,
                                contentDescription = ""
                            )}) {
                                Text(it)
                            }
                            Divider()
                        }
                        info["onlineUrl"]?.let {
                            ListItem(icon = {          Icon(
                                imageVector = Icons.Outlined.Duo,
                                contentDescription = ""
                            )}) {
                                Text(it)
                            }
                            Divider()
                        }
                        info["location"]?.let {
                            ListItem(icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Place,
                                    contentDescription = ""
                                )
                            }) {
                                Text(it)
                            }
                            Divider()
                        }


                    }

                }
            }
        }
    }
}
