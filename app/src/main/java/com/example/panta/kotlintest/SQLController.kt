package com.example.panta.kotlintest
import android.content.ContentValues
import java.util.*

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openDatabase
import android.provider.ContactsContract
import java.lang.Exception

class SQLController(context: Context){
    private val tableName = "DICTIONARY"
    private val sql:SQLHelper = SQLHelper(context)


    fun getDataAll():ArrayList<DataBeans>{   //戻り値として配列を指定する
        val db = sql.writableDatabase
        val cursor:Cursor = db.query(tableName, arrayOf("_id", "TITLE", "READ", "MAINTEXT","READINT"), null, null, null, null, "_id DESC")

        //ここに配列を用意
        var list = ArrayList<DataBeans>()

        var isEof = cursor.moveToFirst()

        while(isEof){
            val id:Int =cursor.getInt(0)
            val title:String = cursor.getString(1)
            val reading:String = cursor.getString(2)
            val mainText:String = cursor.getString(3)
            val readInt = cursor.getInt(4)

            //データオブジェクトにデータを格納
            var data =DataBeans(id,title,reading,mainText,readInt)

            //データオブジェクトを配列に格納
            list.add(data)
            isEof = cursor.moveToNext()
        }
        return list//配列を返す
    }
    fun setData(title:String, reading:String, mainText:String):Boolean{
        val db = sql.writableDatabase
        val values = ContentValues().apply{
            put("TITLE",title)
            put("READ", reading)
            put("MAINTEXT",mainText)
            put("MAINTEXT",mainText)
        }

        try{
            db.insert(tableName,null,values)
        }catch(e:Exception){
            e.printStackTrace()
        }finally{
            db.close()
        }
        return true

    }

    fun delData(position:Int){
        try{
            val db:SQLiteDatabase = sql.writableDatabase
            db.delete(tableName,"_id = " + position.toString(),null)
        }catch(e : Exception){
            e.printStackTrace()
        }

    }

    fun updateData(data:DataBeans){
        val cv = ContentValues().apply{
            put("TITLE",data.title)
            put("READ",data.reading)
            put("MAINTEXT",data.mainText)
        }
        val db:SQLiteDatabase = sql.writableDatabase
        db.update(tableName,cv,"_id = " + data.id,null)

    }

}
