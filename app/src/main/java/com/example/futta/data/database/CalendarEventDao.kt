package com.example.futta.data.database

import androidx.room.*

@Dao
abstract class CalendarEventDao {

    @Insert
    abstract suspend fun insert(calendarEvent: CalendarEventDb)

    @Transaction
    @Query("SELECT * FROM event")
    abstract suspend fun getAll(): List<CalendarEventDb>

    @Query("SELECT * FROM event WHERE id = :id")
    abstract suspend fun getById(id: String): CalendarEventDb?

    @Update
    abstract suspend fun update(calendarEvent: CalendarEventDb)

    @Query("DELETE FROM event WHERE id = :id")
    abstract suspend fun deleteById(id: String)
}