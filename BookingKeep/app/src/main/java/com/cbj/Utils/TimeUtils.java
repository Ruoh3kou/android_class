package com.cbj.Utils;

import com.cbj.DataStruct.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static TimeUtils instance;

    public static synchronized TimeUtils Instance() {

        if (instance == null)
            instance = new TimeUtils();
        return instance;
    }

    // 获取当前日期 yyyy-mm-dd
    public String getCurDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    // 获取当前时间 hh:mm:ss
    public String getCurTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }
}
