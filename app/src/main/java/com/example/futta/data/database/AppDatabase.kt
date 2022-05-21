package com.example.futta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [
        CalendarEventDb::class
    ],
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
}