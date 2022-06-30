package com.example.futta.feature.addEvent.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.futta.R
import com.example.futta.domain.model.*
import com.example.futta.feature.main.navigation.BottomNavigationItem
import com.example.futta.feature.main.ui.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun AddEventScreen(viewModel: AddEventViewModel = viewModel(), navController: NavController) {
    val event by viewModel.bindUi(LocalContext.current).observeAsState(
        UpdateCalendarEventUI(
            id = EventId(UUID.randomUUID().toString()),
            title = "Title",
            description = "Description",
            timeSpan = TimeSpan(
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
            ),
            timeSlot = TimeSlot.CUSTOM,
            cycleType = CycleType.WEEK,
            cancelOnHolidays = true,
            date = LocalDate.now(),
            lectureInfo = LectureInfo.Hybrid("felixUrl", "location", "onlineUrl")
        )
    )
    UpdateEventScreenUi(event, viewModel::onAddEvent, navController)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateEventScreenUi(
    event: UpdateCalendarEventUI,
    saveEvent: (UpdateCalendarEventUI) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val fragmentManager = (context as FragmentActivity).supportFragmentManager
    var title by rememberSaveable { mutableStateOf(value = event.title) }
    var description by rememberSaveable { mutableStateOf(value = event.description) }
    var timeStart by rememberSaveable { mutableStateOf(value = event.timeSpan.timeStart) }
    var timeEnd by rememberSaveable { mutableStateOf(value = event.timeSpan.timeEnd) }
    var date by rememberSaveable { mutableStateOf(value = event.date) }
    val (cycleType, onCycleTypeChange) = remember { mutableStateOf(value = event.cycleType) }
    val (timeSlot, onTimeSlotChange) = remember { mutableStateOf(value = event.timeSlot) }
    var cancelOnHoldidays by rememberSaveable { mutableStateOf(value = event.cancelOnHolidays) }
    val lectureInfoMap = LectureInfo.getValuesBasedOnType(event.lectureInfo)
    var location by rememberSaveable { mutableStateOf(value = lectureInfoMap["location"]) }
    var onlineUrl by rememberSaveable { mutableStateOf(value = lectureInfoMap["onlineUrl"]) }
    var felixUrl by rememberSaveable { mutableStateOf(value = lectureInfoMap["felixUrl"]) }
    val timePickerStart =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(LocalTime.now().hour)
            .setMinute(LocalTime.now().minute)
            .build()
    val timePickerEnd =
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(LocalTime.now().hour)
            .setMinute(LocalTime.now().minute)
            .build()
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(context.getString(R.string.select_date))
        .build()
    timePickerStart.addOnPositiveButtonClickListener {
        timeStart = LocalTime.of(timePickerStart.hour, timePickerStart.minute)
            .truncatedTo(ChronoUnit.MINUTES)
    }
    timePickerEnd.addOnPositiveButtonClickListener {
        timeEnd =
            LocalTime.of(timePickerEnd.hour, timePickerEnd.minute).truncatedTo(ChronoUnit.MINUTES)
    }
    datePicker.addOnPositiveButtonClickListener {
        date = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }
    val openCycleTypeDialog = remember { mutableStateOf(false) }
    val openTimeSlotDialog = remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            content = { Icon(imageVector = Icons.Outlined.Check, contentDescription = "") },
            onClick = {
                saveEvent(
                    event.copy(
                        title = title,
                        description = description,
                        timeSpan = TimeSpan(timeStart, timeEnd),
                        date = date,
                        timeSlot = timeSlot,
                        cycleType = cycleType,
                        cancelOnHolidays = cancelOnHoldidays,
                        lectureInfo = LectureInfo.createBasedOnValues(
                            felixUrl,
                            location,
                            onlineUrl
                        )
                    )
                )
                navController.navigate(BottomNavigationItem.Day.createRoute("$date")) {
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
    }) {


        LazyColumn(state = scrollState) {
            items(count = 1) {
                ListItem {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(context.getString(R.string.title)) },
                        singleLine = true,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }

                ListItem {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(context.getString(R.string.description)) },
                        singleLine = false,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }
                Divider()
                ListItem(
                    text = {
                        Text(
                            text = context.getString(R.string.selected_timeslot) + " $timeSlot".lowercase(),
                            modifier = Modifier.padding(5.dp)
                        )
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = { openTimeSlotDialog.value = true }
                        )
                )
                when (timeSlot) {
                    TimeSlot.CUSTOM -> {
                        Divider()
                        ListItem(
                            text = {
                                Text(
                                    text = context.getString(R.string.from) + " $timeStart",
                                    modifier = Modifier.padding(5.dp)
                                )
                            },
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        timePickerStart.show(fragmentManager, "TimePickerStart")
                                    }
                                )
                        )
                        Divider()
                        ListItem(
                            text = {
                                Text(
                                    text = context.getString(R.string.to) + " $timeEnd",
                                    modifier = Modifier.padding(5.dp)
                                )
                            },
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        timePickerEnd.show(fragmentManager, "TimePickerEnd")
                                    }
                                )
                        )
                    }
                    else -> Unit
                }

                Divider()
                ListItem(
                    text = {
                        Text(
                            text = context.getString(R.string.selected_date) + " $date",
                            modifier = Modifier.padding(5.dp)
                        )
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                datePicker.show(fragmentManager, "DatePicker")
                            }
                        )
                )
                Divider()
                ListItem(
                    text = {
                        Text(
                            text = context.getString(R.string.selected_cycletype) + " $cycleType".lowercase(),
                            modifier = Modifier.padding(5.dp)
                        )
                    },
                    modifier = Modifier
                        .clickable(
                            onClick = { openCycleTypeDialog.value = true }
                        )
                )
                Divider()
                ListItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(text = context.getString(R.string.cancel_on_holidays))
                            Switch(
                                checked = cancelOnHoldidays,
                                onCheckedChange = { cancelOnHoldidays = it }
                            )
                        }
                    })
                Divider()
                felixUrl?.let { url ->
                    ListItem {
                        OutlinedTextField(
                            value = url,
                            onValueChange = { felixUrl = it },
                            label = { Text(context.getString(R.string.felixUrl)) },
                            singleLine = true,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                location?.let { loc ->
                    ListItem {
                        OutlinedTextField(
                            value = loc,
                            onValueChange = { location = it },
                            label = { Text(context.getString(R.string.location)) },
                            singleLine = true,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                onlineUrl?.let { url ->
                    ListItem {
                        OutlinedTextField(
                            value = url,
                            onValueChange = { onlineUrl = it },
                            label = { Text(context.getString(R.string.onlineUrl)) },
                            singleLine = true,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    if (openCycleTypeDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openCycleTypeDialog.value = false
            },
            title = {
                Text(text = context.getString(R.string.select_cycle))
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    CycleType.values().forEach { type ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (type == cycleType),
                                    onClick = { onCycleTypeChange(type) },
                                )
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (type == cycleType),
                                onClick = null
                            )
                            Text(
                                text = type.toString().lowercase(),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openCycleTypeDialog.value = false
                    }
                ) {
                    Text(context.getString(R.string.confirm), color = MaterialTheme.colors.secondaryVariant)
                }
            }
        )
    }
    if (openTimeSlotDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openTimeSlotDialog.value = false
            },
            title = {
                Text(text = context.getString(R.string.select_timeslot))
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    TimeSlot.values().forEach { slot ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (slot == timeSlot),
                                    onClick = { onTimeSlotChange(slot) },
                                )
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (slot == timeSlot),
                                onClick = null
                            )
                            Text(
                                text = slot.toString().lowercase(),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openTimeSlotDialog.value = false
                    }
                ) {
                    Text(context.getString(R.string.confirm), color = MaterialTheme.colors.secondaryVariant)
                }
            }
        )
    }

}
