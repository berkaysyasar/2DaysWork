package com.berkay.a2dayswork.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "Reminder")
data class NMaker(@PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "id") @NotNull var id:Int,
                  @ColumnInfo(name = "remindername") @NotNull var reminderName:String,
                  @ColumnInfo(name="reminderdate") @NotNull var reminderDate:String,
                  @ColumnInfo(name="remindertime") @NotNull var reminderTime:String): Serializable {
}