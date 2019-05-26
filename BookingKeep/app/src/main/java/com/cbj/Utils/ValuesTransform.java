package com.cbj.Utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.cbj.DataBase.TableData;
import com.cbj.DataStruct.Data;
import com.cbj.DataStruct.ListViewItems;

import java.util.ArrayList;
import java.util.List;

public class ValuesTransform {
    /**
     * 从Cursor生成Data对象
     *
     * @param cursor
     * @return
     */
    public static Data transformCursorToData(Cursor cursor) {
        Data data = new Data();
        data.setId(cursor.getInt(cursor.getColumnIndex(TableData._ID)));
        data.setEvent(cursor.getString(cursor.getColumnIndex(TableData.Column_Event)));
        data.setDate(cursor.getString(cursor.getColumnIndex(TableData.Column_Date)));
        data.setMoney(cursor.getInt(cursor.getColumnIndex(TableData.Column_Money)));
        data.setType(cursor.getString(cursor.getColumnIndex(TableData.Column_Type)));
        data.setTime(cursor.getString(cursor.getColumnIndex(TableData.Column_Time)));
        data.setDec(cursor.getString(cursor.getColumnIndex(TableData.Column_Dec)));
        return data;
    }

    /**
     * Data结构转换到ContentValues
     *
     * @param data
     * @return
     */
    public static ContentValues transformDataToContent(Data data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.Column_Date, data.getDate());
        contentValues.put(TableData.Column_Event, data.getEvent());
        contentValues.put(TableData.Column_Money, data.getMoney());
        contentValues.put(TableData.Column_Type, data.getType());
        contentValues.put(TableData.Column_Time, data.getTime());
        contentValues.put(TableData.Column_Dec, data.getDec());
        return contentValues;
    }


    /**
     * List<Data> 转到 List<ListViewItems>
     */
    public static List<ListViewItems> transform_ListData_To_ListViewItems(List<Data> dataList) {
        List<ListViewItems> res = new ArrayList<>();
        if (dataList == null)
            return res;

        // 前一天的日期
        String lastDate = "";
        // 临时变量
        ListViewItems lvi_temp = new ListViewItems();
        Data data_temp;
        // 每一天的总金钱
        int sum_temp = 0;

        for (int i = 0; i < dataList.size(); i++) {
            data_temp = dataList.get(i);
            // 与上次日期不同且不是第一次：保存好上次日期的数据，将临时变量刷新
            if (!lastDate.equals(data_temp.getDate()) && !lastDate.isEmpty()) {
                lvi_temp.setTotal(sum_temp);
                res.add(lvi_temp);
                sum_temp = 0;
                lvi_temp = new ListViewItems();
            }
            lastDate = data_temp.getDate();
            lvi_temp.setDate(lastDate);
            lvi_temp.AddItems(data_temp);
            sum_temp += data_temp.getMoney();
        }
        // 保存最后的
        if (dataList.size() > 0) {
            lvi_temp.setTotal(sum_temp);
            res.add(lvi_temp);
        }
        return res;
    }
}