package com.cbj.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cbj.Adapter.OptionLvAdapter;
import com.cbj.DataBase.DAOClass;
import com.cbj.DataStruct.Classification;
import com.cbj.bookingkeep.R;

import java.util.ArrayList;
import java.util.List;

public class OptionActivity extends AppCompatActivity {
    private Context mContext;
    private ListView listView;
    private Button btn_back;
    public Button btn_addNew;
    private Button btn_income;
    private Button btn_expense;
    private OptionLvAdapter adapter;
    private List<Classification> mList = new ArrayList<>();
    public int curState = 1;// 1支出2收入
    public boolean isMultiple;
    public List<Boolean> selectList;
    public Button btn_del;
    public Button btn_edit;
    public Button btn_undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        mContext = this;
        isMultiple = false;
        selectList = new ArrayList<>();
        listView = findViewById(R.id.option_listView);
        adapter = new OptionLvAdapter(mList, this);
        listView.setAdapter(adapter);
        // 获取数据
        GetClassData();
        // 返回按钮
        btn_back = findViewById(R.id.option_btn_topBar_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 新添加按钮
        btn_addNew = findViewById(R.id.option_btn_add);
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
        // 收入 支出 按钮
        btn_income = findViewById(R.id.option_btn_income);
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curState = 2;
                btn_income.setTextColor(Color.rgb(103, 58, 183));
                btn_expense.setTextColor(Color.rgb(0, 0, 0));
                btn_income.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_expense.setBackground(null);
                GetClassData();
            }
        });
        btn_expense = findViewById(R.id.option_btn_expense);
        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curState = 1;
                btn_expense.setTextColor(Color.rgb(103, 58, 183));
                btn_income.setTextColor(Color.rgb(0, 0, 0));
                btn_expense.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_income.setBackground(null);
                GetClassData();
            }
        });

        btn_del = findViewById(R.id.option_btn_del);
        btn_edit = findViewById(R.id.option_btn_edit);
        btn_undo = findViewById(R.id.option_btn_undo);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultiple) {
                    DAOClass daoClass = new DAOClass(mContext);
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i)) {
                            daoClass.delete(mList.get(i));
                        }
                    }
                    GetClassData();
                    isMultiple = false;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultiple) {
                    showPopupWindow(v);
                }
            }
        });
        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultiple) {
                    selectList.clear();
                    isMultiple = false;
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void SetSelectList() {
        selectList.clear();
        for (int i = 0; i < mList.size(); i++) {
            selectList.add(false);
        }
    }

    private void GetClassData() {
        DAOClass daoClass = new DAOClass(mContext);
        mList.clear();
        List<Classification> tempList = new ArrayList<>();
        if (curState == 1) {
            tempList = new ArrayList<>(daoClass.queryAction("type=?", new String[]{"支出"}));
        } else if (curState == 2) {
            tempList = new ArrayList<>(daoClass.queryAction("type=?", new String[]{"收入"}));
        }
        for (int i = 0; i < tempList.size(); i++) {
            if (!mList.contains(tempList.get(i))) {
                mList.add(tempList.get(i));
            }
        }
        SetSelectList();
        adapter.notifyDataSetChanged();
    }

    // 显示添加窗口
    private void showPopupWindow(View v) {
        backgroundAlpha(0.5f);//让背景变暗
        View popupWindow_view = getLayoutInflater().inflate(R.layout.mypopwindow, null, false);
        Button btn_cancel = popupWindow_view.findViewById(R.id.pop_btn_cancel);
        Button btn_confirm = popupWindow_view.findViewById(R.id.pop_btn_confirm);
        final EditText edit_event = popupWindow_view.findViewById(R.id.pop_edit_text);
        Classification isMultiTemp = new Classification();
        if (isMultiple) {
            for (int i = 0; i < selectList.size(); i++)
                if (selectList.get(i)) {
                    isMultiTemp = mList.get(i);
                    break;
                }
        }
        edit_event.setText(isMultiTemp.getEvent());
        final PopupWindow popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popWindowAnimation);//设置弹出和消失的动画
        popupWindow.setFocusable(true);// 取得焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popupWindow_view, Gravity.BOTTOM, 0, 0);
        // 点击窗体内其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow.isShowing()) {
                    backgroundAlpha(1);
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置消失的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    backgroundAlpha(1);
                    popupWindow.dismiss();
                }
            }
        });
        // 确认
        final Classification finalIsMultiTemp = isMultiTemp;
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_event.getText().toString().equals("")) {
                    Toast.makeText(mContext, "请输入.", Toast.LENGTH_SHORT).show();
                } else {
                    Classification temp = new Classification();
                    temp.setEvent(edit_event.getText().toString());
                    if (curState == 1)
                        temp.setType("支出");
                    else
                        temp.setType("收入");
                    DAOClass daoClass = new DAOClass(mContext);
                    if (isMultiple) {
                        temp.setId(finalIsMultiTemp.getId());
                        daoClass.update(temp);
                        GetClassData();
                    } else {
                        daoClass.insert(temp);
                        GetClassData();
                    }
                    backgroundAlpha(1);
                    popupWindow.dismiss();
                }
            }
        });
    }

    // 使背景变暗
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
