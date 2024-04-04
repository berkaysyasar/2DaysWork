package com.berkay.a2dayswork.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.berkay.a2dayswork.data.entity.NMaker

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    suspend fun loadReminder() : MutableList<NMaker>

    @Insert
    suspend fun save(reminder : NMaker)

    @Update
    suspend fun update(reminder : NMaker)

    @Delete
    suspend fun delete(reminder : NMaker)
}