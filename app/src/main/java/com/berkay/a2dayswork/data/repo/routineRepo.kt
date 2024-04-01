package com.berkay.a2dayswork.data.repo

import com.berkay.a2dayswork.data.datasource.routineDataSource
import com.berkay.a2dayswork.data.entity.RMaker

class routineRepo(var routinesDataSource: routineDataSource) {

    suspend fun save(name:String, time:String) = routinesDataSource.save(name, time)

    suspend fun update(id:Int, name:String, time:String, isdone:Int, isnotificationenabled:Int) = routinesDataSource.update(id, name, time,isdone,isnotificationenabled)

    suspend fun markasdone(id:Int, routinename:String, routinetime:String,isnotificationenabled: Int) = routinesDataSource.markasdone(id, routinename, routinetime,isnotificationenabled)

    suspend fun delete(id:Int) = routinesDataSource.delete(id)

    suspend fun loadRoutine() : MutableList<RMaker> = routinesDataSource.loadRoutines()
}