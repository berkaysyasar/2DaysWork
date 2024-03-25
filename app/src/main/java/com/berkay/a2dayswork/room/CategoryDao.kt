package com.berkay.a2dayswork.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.berkay.a2dayswork.data.entity.CMaker

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun loadCategory() : MutableList<CMaker>

    @Insert
    suspend fun save(category: CMaker)

    @Update
    suspend fun update(category: CMaker)

    @Delete
    suspend fun delete(category: CMaker)
}