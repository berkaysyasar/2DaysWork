package com.berkay.a2dayswork.data.datasource

import com.berkay.a2dayswork.data.entity.NMaker
import com.berkay.a2dayswork.room.ReminderDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class reminderDataSource(var reminderDao: ReminderDao) {

    suspend fun save(reminderName:String, reminderDate:String, reminderTime:String){
        val newReminder = NMaker(0, reminderName, reminderDate, reminderTime)
        reminderDao.save(newReminder)
    }

    suspend fun update(id:Int, remindername:String, reminderdate:String, remindertime:String){
        val newReminder = NMaker(id, remindername, reminderdate, remindertime)
        reminderDao.update(newReminder)
    }

    suspend fun delete(id:Int){
        val deleteReminder = NMaker(id, "", "", "")
        reminderDao.delete(deleteReminder)
    }
    
    suspend fun loadReminders(): MutableList<NMaker> = withContext(Dispatchers.IO) {
        return@withContext reminderDao.loadReminder()
    }
}