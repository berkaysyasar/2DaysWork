package com.berkay.a2dayswork.data.repo

import com.berkay.a2dayswork.data.datasource.reminderDataSource

class reminderRepo(var reminderDataSource: reminderDataSource) {

    suspend fun save(reminderName:String, reminderDate:String, reminderTime:String) = reminderDataSource.save(reminderName, reminderDate, reminderTime)

    suspend fun update(id:Int, remindername:String, reminderdate:String, remindertime:String) = reminderDataSource.update(id, remindername, reminderdate, remindertime)

    suspend fun delete(id:Int) = reminderDataSource.delete(id)

    suspend fun loadReminders() = reminderDataSource.loadReminders()
}