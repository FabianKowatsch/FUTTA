package com.example.futta.data.database

import androidx.room.TypeConverter
import com.example.futta.domain.model.CycleType
import com.example.futta.domain.model.TimeSlot
import java.time.LocalDate
import java.time.LocalTime

object Converters {
    /** Returns the string representation of the [time]. */
    @TypeConverter
    fun timeToDb(time: LocalTime?): String? {
        return time?.toString()
    }

    /** Returns the [LocalTime] represented by the [time]. */
    @TypeConverter
    fun timeFromDb(time: String?): LocalTime? {
        return time?.let {
            LocalTime.parse(it)
        }
    }
    /** Returns the string representation of the [date]. */
    @TypeConverter
    fun dateToDb(date: LocalDate?): String? {
        return date?.toString()
    }

    /** Returns the [LocalDate] represented by the [date]. */
    @TypeConverter
    fun dateFromDb(date: String?): LocalDate? {
        return date?.let {
            LocalDate.parse(it)
        }
    }
    /** Returns the ordinal of the [cycleType]. */
    @TypeConverter
    fun cycleTypeToDb(cycleType: CycleType?): Int? {
        return cycleType?.ordinal
    }
    /** Returns the [CycleType] represented by the [cycleType]. */
    @TypeConverter
    fun cycleTypeFromDb(cycleType: Int?): CycleType? {
        return CycleType.getByOrdinal(cycleType)
    }
}