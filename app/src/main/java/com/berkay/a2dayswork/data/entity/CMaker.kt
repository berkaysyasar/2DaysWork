package com.berkay.a2dayswork.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "category")
class CMaker(@PrimaryKey(autoGenerate = true)
             @ColumnInfo(name = "id") @NotNull var id:Int,
             @ColumnInfo(name = "categoryname") @NotNull var name:String) : Serializable {
}