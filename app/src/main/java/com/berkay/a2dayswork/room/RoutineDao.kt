package com.berkay.a2dayswork.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.data.entity.RMaker

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine")
    fun loadRoutine() : MutableList<RMaker>

    @Insert
    suspend fun save(routine : RMaker)

    @Update
    suspend fun update(routine : RMaker)

    @Update
    suspend fun markasdone(routine : RMaker)

    @Update
    suspend fun isnotificationenabled(routine : RMaker)

    @Delete
    suspend fun delete(routine : RMaker)
}