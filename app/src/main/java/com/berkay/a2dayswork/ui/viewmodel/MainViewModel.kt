package com.berkay.a2dayswork.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.data.repo.categoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var categoryRepo: categoryRepo) : ViewModel(){
    var categorList = MutableLiveData<MutableList<CMaker>>()

    init {
        loadCategory()
    }

    fun delete(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            categoryRepo.delete(id)
            loadCategory()
        }
    }

    fun save(name:String){
        CoroutineScope(Dispatchers.Main).launch {
            categoryRepo.save(name)
            loadCategory()
        }
    }

    fun update(id:Int, name:String){
        CoroutineScope(Dispatchers.Main).launch {
            categoryRepo.update(id, name)
            loadCategory()
        }
    }

    fun loadCategory(){
        CoroutineScope(Dispatchers.Main).launch {
            categorList.value = categoryRepo.loadCategory()
        }
    }
}