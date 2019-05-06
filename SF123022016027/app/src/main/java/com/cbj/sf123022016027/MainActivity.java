package com.cbj.sf123022016027;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView t_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t_level = findViewById(R.id.t_level);
        IntentFilter filter = new IntentFilter(getIntent().ACTION_BATTERY_CHANGED);
        MyReceiver myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);
        myReceiver.setLevel(t_level);
    }
}
