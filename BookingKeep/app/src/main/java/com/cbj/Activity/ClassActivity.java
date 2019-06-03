package com.cbj.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cbj.Adapter.ClassLvAdapter;
import com.cbj.DataBase.DAOClass;
import com.cbj.DataStruct.Classification;
import com.cbj.bookingkeep.R;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private Context mContext;
    private ListView listView;
    private Button btn_back;
    private Button btn_option;
    private ClassLvAdapter adapter;
    private List<String> mList = new ArrayList<>();
    public int curState = 1;// 1支出2收入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        mContext = this;
        listView = findViewById(R.id.class_listview);
        adapter = new ClassLvAdapter(mList, this);
        listView.setAdapter(adapter);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            curState = bundle.getInt("type");
        }
        // 获取数据
        GetClassData();
        // 返回按钮
        btn_back = findViewById(R.id.class_btn_topBar_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置按钮
        btn_option = findViewById(R.id.class_btn_topBar_option);
        btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, OptionActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void GetClassData() {
        DAOClass daoClass = new DAOClass(mContext);
        mList.clear();
        List<Classification> tempList = new ArrayList<>(daoClass.queryAll());
        if (curState == 1)//支出
        {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getType().equals("支出") && !mList.contains(tempList.get(i).getEvent()))
                    mList.add(tempList.get(i).getEvent());
            }
        } else if (curState == 2)//收入
        {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getType().equals("收入") && !mList.contains(tempList.get(i).getEvent()))
                    mList.add(tempList.get(i).getEvent());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetClassData();
    }
}
