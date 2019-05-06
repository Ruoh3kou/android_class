package com.cbj.stuinfo123022016027;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_find;
    private Button btn_delete;
    private Button btn_insert;
    private Button btn_update;
    private EditText et_name;
    private EditText et_sex;
    private EditText et_mobile;
    private EditText et_address;
    private EditText et_no;
    private ListView lv;
    MyBaseAdapter myAdapter;

    private MyHelper helper;

    private ArrayList<student> list;

    private void initUI() {
        btn_delete = findViewById(R.id.bt_delete);
        btn_find = findViewById(R.id.bt_find);
        btn_insert = findViewById(R.id.bt_insert);
        btn_update = findViewById(R.id.bt_update);
        et_address = findViewById(R.id.et_address);
        et_name = findViewById(R.id.et_name);
        et_sex = findViewById(R.id.et_sex);
        et_mobile = findViewById(R.id.et_mobile);
        et_no = findViewById(R.id.et_no);
        lv = findViewById(R.id.lv);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.delete(et_no.getText().toString());
                list.clear();
                list.addAll(helper.findAll());
                myAdapter.notifyDataSetChanged();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.update(et_no.getText().toString(),
                        et_name.getText().toString(),
                        et_mobile.getText().toString(),
                        et_sex.getText().toString(),
                        et_address.getText().toString());
                list.clear();
                list.addAll(helper.findAll());
                myAdapter.notifyDataSetChanged();
            }
        });
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.add(helper.find(et_no.getText().toString()));
                myAdapter.notifyDataSetChanged();
            }
        });
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.insert(et_no.getText().toString(),
                        et_name.getText().toString(),
                        et_mobile.getText().toString(),
                        et_sex.getText().toString(),
                        et_address.getText().toString());
                list.clear();
                list.addAll(helper.findAll());
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        list = new ArrayList<student>();
        helper = new MyHelper(MainActivity.this);
        myAdapter = new MyBaseAdapter();
        lv.setAdapter(myAdapter);
        list.addAll(helper.findAll());
        myAdapter.notifyDataSetChanged();
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.list_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.item_name);
            TextView tv_no = (TextView) view.findViewById(R.id.item_no);
            TextView tv_address = (TextView) view.findViewById(R.id.item_address);
            TextView tv_sex = (TextView) view.findViewById(R.id.item_sex);
            TextView tv_mobile = (TextView) view.findViewById(R.id.item_mobile);

            tv_name.setText(list.get(position).name);
            tv_no.setText(list.get(position).no + "");
            tv_address.setText(list.get(position).address);
            tv_mobile.setText(list.get(position).mobile);
            tv_sex.setText(list.get(position).sex);

            return view;
        }
    }
}
