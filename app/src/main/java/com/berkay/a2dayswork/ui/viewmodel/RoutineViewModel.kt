package com.berkay.a2dayswork.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkay.a2dayswork.data.entity.RMaker
import com.berkay.a2dayswork.data.repo.routineRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(var routineRepo: routineRepo) : ViewModel(){
    var routineList = MutableLiveData<MutableList<RMaker>>()
    private val _updated = MutableLiveData<Boolean>()
    val updated: LiveData<Boolean> get() = _updated

    init {
        loadRoutines()
    }

    fun delete(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            routineRepo.delete(id)
            loadRoutines()
        }
    }

    fun save(name:String, time:String){
        CoroutineScope(Dispatchers.Main).launch {
            routineRepo.save(name, time)
            loadRoutines()
        }
    }

    fun update(id:Int, name:String, time:String, isdone:Int){
        CoroutineScope(Dispatchers.Main).launch {
            routineRepo.update(id, name, time, 1)
            loadRoutines()
        }
    }

    fun markasdone(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            routineRepo.update(id, "", "",0)
            loadRoutines()
        }
    }

    fun loadRoutines(){
        CoroutineScope(Dispatchers.Main).launch {
            routineList.value = routineRepo.loadRoutine()
        }
    }
}