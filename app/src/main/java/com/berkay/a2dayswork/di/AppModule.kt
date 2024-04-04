package com.berkay.a2dayswork.di

import android.content.Context
import androidx.room.Room
import com.berkay.a2dayswork.data.datasource.categoryDataSource
import com.berkay.a2dayswork.data.datasource.reminderDataSource
import com.berkay.a2dayswork.data.datasource.routineDataSource
import com.berkay.a2dayswork.data.repo.categoryRepo
import com.berkay.a2dayswork.data.repo.reminderRepo
import com.berkay.a2dayswork.data.repo.routineRepo
import com.berkay.a2dayswork.room.CategoryDao
import com.berkay.a2dayswork.room.MyDataBase
import com.berkay.a2dayswork.room.ReminderDao
import com.berkay.a2dayswork.room.RoutineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providecategoryRepo( categoryDataSource: categoryDataSource) : categoryRepo{
        return categoryRepo(categoryDataSource)
    }

    @Provides
    @Singleton
    fun provideroutineRepo( routineDataSource: routineDataSource) : routineRepo {
        return routineRepo(routineDataSource)
    }

    @Provides
    @Singleton
    fun providereminderRepo( reminderDataSource: reminderDataSource): reminderRepo {
        return reminderRepo(reminderDataSource)
    }

    @Provides
    @Singleton
    fun providecatagoryDataSource(categoryDao : CategoryDao) : categoryDataSource{
        return categoryDataSource(categoryDao)
    }

    @Provides
    @Singleton
    fun provideroutineDataSource(routineDao : RoutineDao) : routineDataSource{
        return routineDataSource(routineDao)
    }

    @Provides
    @Singleton
    fun providerreminderDataSource(reminderDao: ReminderDao) : reminderDataSource{
        return reminderDataSource(reminderDao)
    }

    @Provides
    @Singleton
    fun providecategoryDao(@ApplicationContext context: Context) : CategoryDao{
        val db = Room.databaseBuilder(context, MyDataBase::class.java,"categoriesdb.db").createFromAsset("categoriesdb.db").build()
        return db.getcategoryDao()
    }

    @Provides
    @Singleton
    fun provideroutineDao(@ApplicationContext context: Context) : RoutineDao{
        val db = Room.databaseBuilder(context, MyDataBase::class.java,"rotuinedb.db").createFromAsset("rotuinedb.db").build()
        return db.getroutineDao()
    }

    @Provides
    @Singleton
    fun providereminderDao(@ApplicationContext context: Context) : ReminderDao{
        val db = Room.databaseBuilder(context, MyDataBase::class.java,"reminder.db").build()
        return db.getreminderDao()
    }


}