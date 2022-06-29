package com.example.futta.feature.day.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.futta.R
import com.example.futta.domain.DeleteEventUseCase
import com.example.futta.domain.model.EventId
import com.example.futta.feature.main.navigation.BottomNavigationItem
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun DayScreen(
    viewModel: DayViewModel = viewModel(),
    date: LocalDate,
    navController: NavController
) {
    val events by viewModel.bindUi(LocalContext.current, date).observeAsState(emptyList())
    DayScreenUi(date = date, events = events, navController = navController)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DayScreenUi(
    date: LocalDate,
    events: List<CalendarEventTeaserUI>,
    navController: NavController
) {
    val dateString = date.toString()
    val eventList = events.toMutableStateList()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.green_3100))
                    .fillMaxWidth()
                    .padding(10.dp, 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dateString,
                    color = colorResource(id = R.color.white)
                )
            }
        },
    ) {
        if (events.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(
                    items = eventList,
                    key = { _, item ->
                        item.id.value
                    }
                ) { _, item ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                eventList.remove(item)
                                scope.launch { DeleteEventUseCase()(item.id) }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.EndToStart -> MaterialTheme.colors.error
                                else -> Color.Transparent
                            }
                            val tintColor = when (state.dismissDirection) {
                                DismissDirection.EndToStart -> Color.White
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete",
                                    tint = tintColor,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            CalendarEventTeaserItem(item, navController)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                    Divider()
                }
            }
        }
        else
            Text(context.getString(R.string.no_events))
    }
}
