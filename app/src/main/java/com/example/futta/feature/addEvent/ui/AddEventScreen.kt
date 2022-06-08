package com.example.futta.feature.addEvent.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.futta.domain.model.CycleType
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalUnit
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toDuration

@Composable
fun AddEventScreen() {
    EditEventScreenUi()
}

@Composable
fun EditEventScreenUi() {
    val fragmentManager = (LocalContext.current as FragmentActivity).supportFragmentManager
    var title by rememberSaveable { mutableStateOf(value = "") }
    var description by rememberSaveable { mutableStateOf(value = "") }
    var timeStart by rememberSaveable { mutableStateOf(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))}
    var timeEnd by rememberSaveable { mutableStateOf(LocalTime.now().truncatedTo(ChronoUnit.MINUTES)) }
    var date by rememberSaveable { mutableStateOf(LocalDate.now()) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(CycleType.WEEK) }
    var cancelOnHoldidays by rememberSaveable { mutableStateOf(value = "") }
    var location by rememberSaveable { mutableStateOf(value = "") }
    var onlineUrl by rememberSaveable { mutableStateOf(value = "") }
    var felixUrl by rememberSaveable { mutableStateOf(value = "") }
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
        .setTitleText("Select date")
        .build()
    timePickerStart.addOnPositiveButtonClickListener {
        timeStart = LocalTime.of(timePickerStart.hour, timePickerStart.minute).truncatedTo(ChronoUnit.MINUTES)
    }
    timePickerEnd.addOnPositiveButtonClickListener {
        timeEnd = LocalTime.of(timePickerEnd.hour, timePickerEnd.minute).truncatedTo(ChronoUnit.MINUTES)
    }
    datePicker.addOnPositiveButtonClickListener {
        date = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    Column() {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("title") },
            singleLine = true,
            modifier = Modifier.padding(5.dp)
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("description") },
            singleLine = false,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "$timeStart",
            modifier = Modifier
                .clickable(
                    onClick = {
                        timePickerStart.show(fragmentManager, "TimePicker")
                    }
                )
                .padding(5.dp)
        )
        Text(
            text = "$timeEnd",
            modifier = Modifier
                .clickable(
                    onClick = {
                        timePickerStart.show(fragmentManager, "TimePicker")
                    }
                )
                .padding(5.dp)
        )
        Text(
            text = "$date",
            modifier = Modifier
                .clickable(
                    onClick = {
                        datePicker.show(fragmentManager, "DatePicker")
                    }
                )
                .padding(5.dp)
        )
        Column(Modifier.selectableGroup()) {
            CycleType.values().forEach { type ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (type == selectedOption),
                            onClick = { onOptionSelected(type) },
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (type == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = type.toString(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

    }
}