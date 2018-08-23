package com.example.panta.kotlintest;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper{
    static final int DB_VERSION = 2;
    static final String DB_NAME = "sqliteDB1333311";
    static final String DROP_TABLE = "drop table mytable";

    public SQLHelper(Context context){

        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS DICTIONARY(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,TITLE TEXT,READ TEXT,MAINTEXT TEXT,READINT INTEGER)");//テーブル２の_ctidを外部キーとして設定する
        //db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORY(_ctid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,CATEGORYNAME TEXT)");//テーブル２
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
