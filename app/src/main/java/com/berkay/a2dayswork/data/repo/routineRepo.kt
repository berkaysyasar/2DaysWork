package com.berkay.a2dayswork.data.repo

import com.berkay.a2dayswork.data.datasource.routineDataSource
import com.berkay.a2dayswork.data.entity.RMaker

class routineRepo(var routinesDataSource: routineDataSource) {

    suspend fun save(name:String, time:String) = routinesDataSource.save(name, time)

    suspend fun update(id:Int, name:String, time:String, isdone:Int) = routinesDataSource.update(id, name, time,isdone)

    suspend fun markasdone(id:Int, routinename:String, routinetime:String) = routinesDataSource.markasdone(id, routinename, routinetime)

    suspend fun delete(id:Int) = routinesDataSource.delete(id)

    suspend fun loadRoutine() : MutableList<RMaker> = routinesDataSource.loadRoutines()
}