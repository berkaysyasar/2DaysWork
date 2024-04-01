package com.berkay.a2dayswork.data.datasource

import com.berkay.a2dayswork.data.entity.RMaker
import com.berkay.a2dayswork.room.RoutineDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class routineDataSource(var routineDao: RoutineDao) {
    suspend fun save(routineName:String, routineTime:String) {
        val newRoutine = RMaker(0, routineName, routineTime,0,0)
        routineDao.save(newRoutine)
    }

    suspend fun update(id:Int, routinename:String, routinetime:String, isdone:Int, isnotificationenabled:Int) {
        val newRoutine = RMaker(id, routinename, routinetime,isdone,isnotificationenabled)
        routineDao.update(newRoutine)
    }

    suspend fun delete(id:Int){
        val deleteRoutine = RMaker(id, "", "",0,0)
        routineDao.delete(deleteRoutine)
    }
    suspend fun markasdone(id:Int,routinename:String, routinetime:String, isnotificationenabled: Int){
        val updateRoutine = RMaker(id, routinename, routinetime,1, isnotificationenabled)
        routineDao.markasdone(updateRoutine)
    }


    suspend fun loadRoutines(): MutableList<RMaker> = withContext(Dispatchers.IO){
        return@withContext routineDao.loadRoutine()
    }
}