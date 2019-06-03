package com.cbj.DataBase;

import android.provider.BaseColumns;

public class TableClass implements BaseColumns {
    /**
     * 数据库信息
     */
    public static final String SqlLite_Name = "BookingKeep.db";
    public static final int SqlLite_Verson = 3;
    /**
     * 表名、字段
     */
    public static final String Table_Name = "Class";
    public static final String Column_Event = "event";
    public static final String Column_Type = "type";

}
