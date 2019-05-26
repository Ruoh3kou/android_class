package com.cbj.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cbj.bookingkeep.R;

public class HelloActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (IsFirstRun()) {
            Button button = findViewById(R.id.btn_entry);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangToActive(MainActivity.class);
                }
            });
        } else {
            ChangToActive(MainActivity.class);
        }

    }

    void ChangToActive(Class target) {
        Intent mIntent = new Intent();
        mIntent.setClass(HelloActivity.this, target);
        HelloActivity.this.startActivity(mIntent);
        HelloActivity.this.finish();
    }

    boolean IsFirstRun() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirst) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            return true;
        }
        return false;
    }
}