package com.berkay.a2dayswork.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.berkay.a2dayswork.data.entity.CMaker

val database_name = "Veritabanim"
val table_name = "Kategoriler"
val col_name = "kategoriname"
val col_id = "id"

class dbhelper(var context: Context): SQLiteOpenHelper(context, database_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + table_name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col_name + " VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Veritabanı yükseltmek için kullanılır
    }

    fun insertData(CMaker : CMaker){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_name, CMaker.categoryname)
        var sonuc = db.insert(table_name, null, cv)
        if (sonuc == -1.toLong())
            Toast.makeText(context, "Hatalı", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        db.close()
    }

    fun readData() : MutableList<CMaker>{
        val list: MutableList<CMaker> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $table_name"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val cMaker = CMaker()
                val idColumIndex = result.getColumnIndex(col_id)
                val nameColumIndex = result.getColumnIndex(col_name)

                if(idColumIndex >= 0){
                    cMaker.id = result.getString(idColumIndex).toInt()
                }
                if(nameColumIndex >= 0){
                    cMaker.categoryname = result.getString(nameColumIndex)
                }
                list.add(cMaker)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData(categoryId: String): Boolean{
        val db =  this.writableDatabase
        val whereClause = "$col_name = ?"
        val whereArgs = arrayOf(categoryId.toString())

        val result = db.delete(table_name, whereClause, whereArgs)
        db.close()
        return result > 0
    }

}