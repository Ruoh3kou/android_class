package com.cbj.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    DataHelper(Context context) {
        super(context, "BookingKeep.db", null, 3);
    }

    // 创建 Data 表
    private static final String Create_Table_Data = "create table " +
            TableData.Table_Name + "(" +
            TableData._ID + " integer primary key autoincrement," +
            TableData.Column_Event + " text," +
            TableData.Column_Money + " integer," +
            TableData.Column_Type + " integer," +
            TableData.Column_Date + " text," +
            TableData.Column_Time + " text, " +
            TableData.Column_Dec + " text "
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
