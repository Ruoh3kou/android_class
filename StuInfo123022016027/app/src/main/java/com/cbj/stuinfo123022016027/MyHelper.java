package com.cbj.stuinfo123022016027;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "student_db", null, 2);
    }

    public long insert(String no, String name, String mobile, String sex, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_no", no);
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("sex", sex);
        values.put("address", address);
        long id = db.insert("student", null, values);
        db.close();
        return id;
    }

    public long update(String no, String name, String mobile, String sex, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_no", no);
        values.put("name", name);
        values.put("mobile", mobile);
        values.put("sex", sex);
        values.put("address", address);
        long id = db.update("student", values, "_no=?", new String[]{no + ""});
        db.close();
        return id;
    }

    public int delete(String no) {
        SQLiteDatabase db = this.getWritableDatabase();
        int number = db.delete("student", "_no=?", new String[]{no});
        db.close();
        return number;
    }

    public ArrayList<student> findAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student", null);
        ArrayList<student> list = new ArrayList<student>();
        while (cursor.moveToNext()) {
            student stu = new student();
            stu.name = cursor.getString(cursor.getColumnIndex("name"));
            stu.address = cursor.getString(cursor.getColumnIndex("address"));
            stu.sex = cursor.getString(cursor.getColumnIndex("sex"));
            stu.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            stu.no = cursor.getString(cursor.getColumnIndex("_no"));
            list.add(stu);
        }
        cursor.close();
        db.close();
        return list;
    }

    public student find(String no) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("student",
                null,
                "_no=?",
                new String[]{no},
                null, null, null
        );
        boolean res = cursor.moveToNext();
        if (res) {
            student stu = new student();
            stu.name = cursor.getString(cursor.getColumnIndex("name"));
            stu.address = cursor.getString(cursor.getColumnIndex("address"));
            stu.sex = cursor.getString(cursor.getColumnIndex("sex"));
            stu.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
            stu.no = cursor.getString(cursor.getColumnIndex("_no"));
            return stu;
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student(" +
                "_no VARCHAR(20) PRIMARY KEY NOT NULL," +
                "name VARCHAR(20) NOT NULL," +
                "mobile VARCHAR(20)," +
                "sex VARCHAR(2) NOT NULL," +
                "address VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
