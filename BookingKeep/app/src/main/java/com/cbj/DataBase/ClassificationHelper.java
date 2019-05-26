package com.cbj.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassificationHelper extends SQLiteOpenHelper {
    public ClassificationHelper(Context context) {
        super(context, "BookingKeep.db", null, 3);
    }

    // 创建 Classification 表
    public static final String Create_Table_Classification = "create table " +
            TableClassification.Table_Name + "(" +
            TableClassification._ID + " integer primary key autoincrement," +
            TableClassification.Column_Event + " text," +
            TableClassification.Column_Type + " text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Classification);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
