package com.vvdi.foodorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context)
    {
        // конструктор суперкласса
        super(context, "myDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "onCreateTable: ");
        // создаем таблицу с полями
        db.execSQL("create table ordersTable ("
                + "id integer primary key autoincrement,"
                + "orderId text,"
                + "status text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { }
}