package com.cbj.storage123022016027;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUser;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Map<String, String> userInfo = saveUserPwd.getUserInfo(this);
        if (userInfo != null) {
            etUser.setText(userInfo.get("userID"));
            etPwd.setText(userInfo.get("pwd"));
        }
    }

    private void initView() {
        etUser = (EditText) findViewById(R.id.edit_user);
        etPwd = (EditText) findViewById(R.id.edit_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userNum = etUser.getText().toString().trim();
        String userPwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(userNum)) {
            Toast.makeText(this, "please input your qq number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            Toast.makeText(this, "please input your qq password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 登录提示
        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
        boolean isSaveSuccess = saveUserPwd.saveUserInfo(this,userNum,userPwd);
        if(isSaveSuccess){
            Toast.makeText(this, "Save qq Successful!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Save qq Failed!", Toast.LENGTH_SHORT).show();
        }

        // 跳转场景
        Intent intent = new Intent(this,weather.class);
        startActivity(intent);
    }
}

