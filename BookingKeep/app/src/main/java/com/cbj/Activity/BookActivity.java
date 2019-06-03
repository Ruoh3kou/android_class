package com.cbj.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cbj.DataBase.DAOData;
import com.cbj.DataStruct.Data;
import com.cbj.Utils.JumpActivityUtils;
import com.cbj.Utils.TimeUtils;
import com.cbj.bookingkeep.R;

public class BookActivity extends AppCompatActivity {
    Context mContext;
    private Button btn_BackToMainAt;
    private EditText edit_money;
    private EditText edit_dec;
    private RelativeLayout rl_sort;
    private Button btn_save;
    private Button btn_income;
    private Button btn_expense;
    private TextView text_event;
    // 判断是新建/修改
    private Boolean isNew = true;
    // 用以保存Data
    Data curData = new Data();

    // 当前状态（收入、支出）
    private enum CurIncExpMode {
        incomeMode, expenseMode
    }

    CurIncExpMode curIncExpMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_book);
        curIncExpMode = CurIncExpMode.expenseMode;
        InitView();
        InitInfo();
    }

    private void InitInfo() {
        Bundle bundle = this.getIntent().getExtras();
        // 编辑信息
        if (bundle != null) {
            isNew = false;
            curData.setId(bundle.getInt("id"));
            curData.setMoney(bundle.getInt("money"));
            curData.setDec(bundle.getString("dec"));
            curData.setType(bundle.getString("type"));
            curData.setEvent(bundle.getString("event"));
            curData.setDate(bundle.getString("date"));
            curData.setTime(bundle.getString("time"));

            edit_dec.setText(curData.getDec());
            edit_money.setText(String.format("%d", Math.abs(curData.getMoney())));
            text_event.setText(curData.getEvent());
            if (curData.getType().equals("收入"))
                curIncExpMode = CurIncExpMode.incomeMode;
            else if (curData.getType().equals("支出"))
                curIncExpMode = CurIncExpMode.expenseMode;

            // 收入模式
            if (curIncExpMode.equals(CurIncExpMode.incomeMode)) {
                edit_money.setTextColor(Color.rgb(255, 0, 0));
            }
            // 支出模式
            else if (curIncExpMode.equals(CurIncExpMode.expenseMode)) {
                edit_money.setTextColor(Color.rgb(0, 0, 0));
            }

        }

        // SAVE 按钮
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAOData daoData = new DAOData(mContext);
                if (edit_dec.getText().toString().equals("") || edit_money.getText().toString().equals("") || text_event.getText().toString().equals("")) {
                    Toast.makeText(mContext, "信息填写不完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                curData.setDate(TimeUtils.Instance().getCurDate());
                curData.setTime(TimeUtils.Instance().getCurTime());
                curData.setDec(edit_dec.getText().toString());
                curData.setEvent(text_event.getText().toString());
                if (curIncExpMode.equals(CurIncExpMode.incomeMode)) {
                    curData.setMoney(Integer.parseInt(edit_money.getText().toString()));
                } else if (curIncExpMode.equals(CurIncExpMode.expenseMode)) {
                    curData.setMoney(-1 * Integer.parseInt(edit_money.getText().toString()));
                }
                curData.setType(getCurState());
                if (isNew) {
                    daoData.insert(curData);
                    Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    daoData.update(curData);
                    Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 分类按钮
        rl_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ClassActivity.class);
                // 1 支出  2 收入
                Bundle bundle = new Bundle();
                if (curIncExpMode.equals(CurIncExpMode.expenseMode)) {
                    bundle.putInt("type", 1);
                } else if (curIncExpMode.equals(CurIncExpMode.incomeMode)) {
                    bundle.putInt("type", 2);
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        // 收入 支出 按钮
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curIncExpMode = CurIncExpMode.incomeMode;
                btn_income.setTextColor(Color.rgb(103, 58, 183));
                btn_expense.setTextColor(Color.rgb(0, 0, 0));
                btn_income.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_expense.setBackground(null);
                edit_money.setTextColor(Color.rgb(255, 0, 0));
            }
        });
        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curIncExpMode = CurIncExpMode.expenseMode;
                btn_expense.setTextColor(Color.rgb(103, 58, 183));
                btn_income.setTextColor(Color.rgb(0, 0, 0));
                btn_expense.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_income.setBackground(null);
                edit_money.setTextColor(Color.rgb(0, 0, 0));
            }
        });

        // 返回主菜单
        btn_BackToMainAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpActivityUtils.Instance().BookBackToMain(BookActivity.this);
            }
        });
    }

    private void InitView() {
        btn_BackToMainAt = findViewById(R.id.book_btn_topBar_back);
        edit_money = findViewById(R.id.book_edit_money);
        edit_dec = findViewById(R.id.book_edit_dec);
        btn_income = findViewById(R.id.book_btn_income);
        btn_expense = findViewById(R.id.book_btn_expense);
        btn_save = findViewById(R.id.book_btn_save);
        rl_sort = findViewById(R.id.book_rl_sort);
        text_event = findViewById(R.id.book_text_event);
    }

    /**
     * 获取当前支出、收入状态
     */
    private String getCurState() {
        if (curIncExpMode.equals(CurIncExpMode.incomeMode)) {
            return "收入";
        }
        return "支出";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
            text_event.setText(data.getStringExtra("event"));
    }
}
