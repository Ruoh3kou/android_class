package com.cbj.saveqqpwd;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileSaveQQ {
    // save qq number and pwd into data.txt.
    public static boolean saveUserInfo(Context context, String number, String pwd) {
        try {
            FileOutputStream fos = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fos.write((number + ":" + pwd).getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // get qq number and pwd from data.txt.
    public static Map<String, String> getUserInfo(Context context) {
        String content = "";
        try {
            FileInputStream fis =context.openFileInput("data.txt");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            content = new String(buffer);
            Map<String,String> userMap = new HashMap<String,String>();
            String[] infos =content.split(":");
            userMap.put("number",infos[0]);
            userMap.put("pwd",infos[1]);
            fis.close();
            return userMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
