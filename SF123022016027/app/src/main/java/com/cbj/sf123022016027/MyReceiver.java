package com.cbj.sf123022016027;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MyReceiver extends BroadcastReceiver {
    private TextView t_level;

    public void setLevel(TextView t) {
        this.t_level = t;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int curLevel = intent.getExtras().getInt("level");
        int totalScale = intent.getExtras().getInt("scale");
        int persent = curLevel * 100 / totalScale;
        t_level.setText(persent + "%");
        if (persent <= 5) {
            Toast ss = Toast.makeText(context, "电量不足！", Toast.LENGTH_SHORT);
            ss.show();
        }
    }
}
