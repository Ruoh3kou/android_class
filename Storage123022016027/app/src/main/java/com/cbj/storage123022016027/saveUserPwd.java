package com.cbj.storage123022016027;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class saveUserPwd extends Activity {
    // save qq number and pwd into data.txt.
    public static boolean saveUserInfo(Context context, String number, String pwd) {
        try {
            SharedPreferences sp = context.getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userID", number);
            editor.putString("pwd", pwd);
            editor.commit();
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
            SharedPreferences sp = context.getSharedPreferences("data",MODE_PRIVATE);
            Map<String, String> userMap = new HashMap<String, String>();
            userMap.put("userID", sp.getString("userID",""));
            userMap.put("pwd", sp.getString("pwd",""));
            return userMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
