package com.berkay.a2dayswork.data.repo

import com.berkay.a2dayswork.data.datasource.categoryDataSource
import com.berkay.a2dayswork.data.entity.CMaker
import com.berkay.a2dayswork.room.CategoryDao

class categoryRepo(var categoryDataSource: categoryDataSource) {

    suspend fun save(name: String) = categoryDataSource.save(name)

    suspend fun update(id:Int, name: String) = categoryDataSource.update(id,name)

    suspend fun delete(id:Int) = categoryDataSource.delete(id)

    suspend fun loadCategory() : MutableList<CMaker> = categoryDataSource.loadcategory()
}