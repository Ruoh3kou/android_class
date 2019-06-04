package com.cbj.DataBase;

import android.provider.BaseColumns;

public class TableData implements BaseColumns {
    /**
     * 数据库信息
     */
    public static final String SqlLite_Name = "BookingKeep.db";
    public static final int SqlLite_Verson = 3;
    /**
     * 表名、字段
     */
    public static final String Table_Name = "data";
    public static final String Column_Event = "event";
    public static final String Column_Date = "date";
    public static final String Column_Type = "type";
    public static final String Column_Money = "money";
    public static final String Column_Time = "time";
    public static final String Column_Dec = "dec";
    /**
     * 时间字段的格式
     */
    public static final String Date_Format = "YYYY-MM-DD";
    /**
     * 时间字段的降序，采用date函数比较
     */
    public static final String Order_By = "date(" + Column_Date + ") desc";
}
