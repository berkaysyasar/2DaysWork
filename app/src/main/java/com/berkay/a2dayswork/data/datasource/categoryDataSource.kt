package com.berkay.a2dayswork.data.datasource

import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.room.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class categoryDataSource(var categoryDao: CategoryDao) {

    suspend fun save(categoryName:String) {
        val newCategory = CMaker(0, categoryName)
        categoryDao.save(newCategory)
    }

    suspend fun update(id:Int, categoryname:String) {
        val newCategory = CMaker(id, categoryname)
        categoryDao.update(newCategory)
    }

    suspend fun delete(id:Int) {
        val deleteCategory = CMaker(id, "")
        categoryDao.delete(deleteCategory)
    }

    suspend fun loadcategory(): MutableList<CMaker> = withContext(Dispatchers.IO){
        return@withContext categoryDao.loadCategory()
    }
}