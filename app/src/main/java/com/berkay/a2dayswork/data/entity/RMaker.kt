package com.berkay.a2dayswork.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "Routine")
data class RMaker(  @PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id") @NotNull var id:Int,
                    @ColumnInfo(name = "routinename") @NotNull var routinename:String,
                    @ColumnInfo(name="routinetime") @NotNull var routinetime:String,
                    @ColumnInfo(name="isDone") @NotNull var isDone:Int) : Serializable {
}