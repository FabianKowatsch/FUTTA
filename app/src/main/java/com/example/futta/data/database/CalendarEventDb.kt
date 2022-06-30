package com.example.futta.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.futta.domain.model.CycleType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "event")
data class CalendarEventDb(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val timeStart: LocalTime,
    val timeEnd: LocalTime,
    val date: LocalDate,
    val cycleType: CycleType,
    val cancelOnHolidays: Boolean,
    val felixUrl: String?,
    val location: String?,
    val onlineUrl: String?
    )
