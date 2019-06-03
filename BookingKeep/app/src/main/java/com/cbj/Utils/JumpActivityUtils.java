package com.cbj.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cbj.Activity.BookActivity;
import com.cbj.Activity.ClassActivity;
import com.cbj.Activity.MainActivity;
import com.cbj.DataStruct.Data;

public class JumpActivityUtils {
    private static JumpActivityUtils instance;

    public static synchronized JumpActivityUtils Instance() {
        if (instance == null)
            instance = new JumpActivityUtils();
        return instance;
    }

    public void BookBackToMain(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void MainToBookAdd(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void MainToBookUpdate(Context context, Data data) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", data.getId());
        bundle.putInt("money", data.getMoney());
        bundle.putString("type", data.getType());
        bundle.putString("event", data.getEvent());
        bundle.putString("date", data.getDate());
        bundle.putString("time", data.getTime());
        bundle.putString("dec", data.getDec());

        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, BookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
