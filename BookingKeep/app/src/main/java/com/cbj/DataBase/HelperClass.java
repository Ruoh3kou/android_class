package com.cbj.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperClass extends SQLiteOpenHelper {
    public HelperClass(Context context) {
        super(context, "BookingKeep.db", null, 3);
    }

    // 创建 Classification 表
    public static final String Create_Table_Class = "create table " +
            TableClass.Table_Name + "(" +
            TableClass._ID + " integer primary key autoincrement," +
            TableClass.Column_Event + " text," +
            TableClass.Column_Type + " text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
