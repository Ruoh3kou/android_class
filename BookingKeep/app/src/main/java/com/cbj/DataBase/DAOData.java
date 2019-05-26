package com.cbj.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cbj.Utils.ValuesTransform;
import com.cbj.DataStruct.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/7 0007.
 * blog: http://blog.csdn.net/hexingen
 * <p>
 * Dao的实现类，对Message表进行增删查改操作。
 */
public class DAOData implements DAO<Data> {
    private Context context;
    private DataHelper dataHelper;

    public DAOData(Context context) {
        this.context = context;
        this.dataHelper = new DataHelper(this.context);
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
            sqLiteDatabase = this.dataHelper.getReadableDatabase();
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
            sqLiteDatabase = this.dataHelper.getWritableDatabase();
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

    }
}