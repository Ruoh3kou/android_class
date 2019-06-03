package com.cbj.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cbj.DataStruct.Data;
import com.cbj.Utils.ValuesTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DAOData implements DAO<Data> {
    private Context context;
    private HelperData helperData;

    public DAOData(Context context) {
        this.context = context;
        this.helperData = new HelperData(this.context);
    }

    @Override
    public List<Data> queryAll() {
        return queryAction(null, null);
    }

    @Override
    public List<Data> queryAction(String selection,
                                  String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<Data> list = null;
        try {
            sqLiteDatabase = this.helperData.getReadableDatabase();
            cursor = sqLiteDatabase.query(TableData.Table_Name, null, selection, selectionArgs, null, null, TableData.Order_By);
            list = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(ValuesTransform.transformCursorToData(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return list;
    }

    public List<Data> queryByMonth(int year, int month) {
        Date date = new GregorianCalendar(year, month, 1).getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String temp = simpleDateFormat.format(date);
        return queryAction("date like ? ", new String[]{temp + "%"});
    }

    @Override
    public void delete() {

    }

    @Override
    public void insert(Data data) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.helperData.getWritableDatabase();
            sqLiteDatabase.insert(TableData.Table_Name, null, ValuesTransform.transformDataToContent(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public void update(Data data) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.helperData.getWritableDatabase();
            sqLiteDatabase.update(TableData.Table_Name, ValuesTransform.transformDataToContent(data), "_id=?", new String[]{data.getId() + ""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }
}