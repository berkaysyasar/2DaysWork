package com.berkay.a2dayswork.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.data.entity.RMaker

@Database(entities = [CMaker::class, RMaker::class], version = 1)
abstract class MyDataBase :RoomDatabase() {

    abstract fun getcategoryDao(): CategoryDao

    abstract fun getroutineDao(): RoutineDao
}