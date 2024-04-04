package com.berkay.a2dayswork.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkay.a2dayswork.data.entity.NMaker
import com.berkay.a2dayswork.data.repo.reminderRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(var reminderRepo: reminderRepo) : ViewModel(){
    var reminderList = MutableLiveData<MutableList<NMaker>>()

    init {
        loadReminders()
    }

    fun delete(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            reminderRepo.delete(id)
            loadReminders()
        }
    }

    fun save(name:String, time:String, date: String){
        CoroutineScope(Dispatchers.Main).launch {
            reminderRepo.save(name, time, date)
            loadReminders()
        }
    }

    fun update(id:Int, name:String, time:String, date: String){
        CoroutineScope(Dispatchers.Main).launch {
            reminderRepo.update(id, name, time, date)
            loadReminders()
        }
    }

    fun loadReminders(){
        CoroutineScope(Dispatchers.Main).launch {
            reminderList.value = reminderRepo.loadReminders()
        }
    }
}