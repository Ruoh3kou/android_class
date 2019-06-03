package com.cbj.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cbj.DataStruct.Classification;
import com.cbj.Utils.ValuesTransform;

import java.util.ArrayList;
import java.util.List;

public class DAOClass implements DAO<Classification> {
    private Context context;
    private HelperClass helperCF;

    public DAOClass(Context context) {
        this.context = context;
        this.helperCF = new HelperClass(this.context);
    }

    @Override
    public List<Classification> queryAll() {
        return queryAction(null, null);
    }

    @Override
    public List<Classification> queryAction(String selection,
                                            String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<Classification> list = null;
        try {
            sqLiteDatabase = this.helperCF.getReadableDatabase();
            cursor = sqLiteDatabase.query(TableClass.Table_Name, null, selection, selectionArgs, null, null, null);
            list = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(ValuesTransform.transformCursorToCF(cursor));
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


    @Override
    public void delete() {

    }

    @Override
    public void insert(Classification data) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.helperCF.getWritableDatabase();
            sqLiteDatabase.insert(TableClass.Table_Name, null, ValuesTransform.transformCFToContent(data));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public void update(Classification data) {

    }
}