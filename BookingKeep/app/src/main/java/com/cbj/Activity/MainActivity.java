package com.cbj.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbj.Adapter.MyLvAdapter;
import com.cbj.DataBase.DAOData;
import com.cbj.DataStruct.ListViewItems;
import com.cbj.MyView.CircleChart.CCConfig;
import com.cbj.MyView.CircleChart.CCInfo;
import com.cbj.MyView.CircleChart.MyCircleChart;
import com.cbj.MyView.ListView.MyListView;
import com.cbj.Utils.ColorUtils;
import com.cbj.Utils.ValuesTransform;
import com.cbj.bookingkeep.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    Context mContext;
    private List<ListViewItems> mList = new ArrayList<>();
    private MyLvAdapter mMyLvAdapter;

    // value
    private int curMonth = 0;
    private int curYear = 0;
    private int curSelectMonth = 0;

    private enum CurChartView {
        incomeChart, expenseChart
    }

    CurChartView curChartView;

    // top-bar
    private TextView t_curMonthTotalIncome;
    private TextView t_curMonthTotalExpense;
    private Button btn_last;
    private Button btn_next;
    private TextView t_curMonth;

    // main-view
    private LinearLayout view_main;
    private LinearLayout view_noData;
    private MyListView mMyListView;

    // chart-view
    private LinearLayout view_chart;
    private MyCircleChart My_cc;
    private Button btn_cc_income;
    private Button btn_cc_expense;

    // bottom-bar
    private Button btn_book;
    private Button btn_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        curChartView = CurChartView.incomeChart;
        initView();
        loadData();
    }

    private void initView() {
        // 初始化UI控件
        t_curMonth = findViewById(R.id.t_curMonth);
        btn_last = findViewById(R.id.btn_last);
        btn_next = findViewById(R.id.btn_next);
        btn_book = findViewById(R.id.btn_book);
        btn_chart = findViewById(R.id.btn_chart);
        Button btn_addBook = findViewById(R.id.btn_addBook);
        view_noData = findViewById(R.id.noData);
        t_curMonthTotalExpense = findViewById(R.id.t_expense);
        t_curMonthTotalIncome = findViewById(R.id.t_income);
        view_main = findViewById(R.id.view_main);
        // 环形图
        view_chart = findViewById(R.id.view_chart);
        view_chart.setVisibility(View.INVISIBLE);
        My_cc = findViewById(R.id.mCC_Chart);
        btn_cc_expense = findViewById(R.id.btn_cc_expense);
        btn_cc_income = findViewById(R.id.btn_cc_income);
        // 初始化 list view 相关
        mMyListView = findViewById(R.id.lv);
        mMyLvAdapter = new MyLvAdapter(mList, this);
        mMyListView.setAdapter(mMyLvAdapter);
        mMyListView.setOnLoadMoreListener(new MyListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });
        // 初始化 月份 控件
        Calendar calendar = Calendar.getInstance();
        curMonth = calendar.get(Calendar.MONTH) + 1;
        curYear = calendar.get(Calendar.YEAR);
        setCurSelectMonth(curMonth);
        // 初始化 获取当月 数据
        GetCurMonthData();
        //  无数据的提示
        setNoDataSign();
        //  绑定按钮事件
        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前时间
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//                Date date = new Date(System.currentTimeMillis());
//                String[] date_time = simpleDateFormat.format(date).split(" ");
//                Data data = new Data();
//                data.setDate(date_time[0]);
//                data.setTime(date_time[1]);
//                data.setEvent("聚餐");
//                data.setType("支出");
//                data.setMoney(-30);
//                DAOData daoData = new DAOData(mContext);
//                daoData.insert(data);
            }
        });
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindLastBtn();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindNextBtn();
            }
        });
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_main.setVisibility(View.VISIBLE);
                Drawable img_book2 = ResourcesCompat.getDrawable(getResources(), R.drawable.book2, null);
                v.setBackground(img_book2);
                view_chart.setVisibility(View.INVISIBLE);
                Drawable img_chart1 = ResourcesCompat.getDrawable(getResources(), R.drawable.chart1, null);
                btn_chart.setBackground(img_chart1);
            }
        });
        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitCircleChart();
                view_chart.setVisibility(View.VISIBLE);
                Drawable img_chart2 = ResourcesCompat.getDrawable(getResources(), R.drawable.chart2, null);
                v.setBackground(img_chart2);
                view_main.setVisibility(View.INVISIBLE);
                Drawable img_book1 = ResourcesCompat.getDrawable(getResources(), R.drawable.book1, null);
                btn_book.setBackground(img_book1);
            }
        });
        btn_cc_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curChartView = CurChartView.incomeChart;
                InitCircleChart();

                btn_cc_income.setTextColor(Color.rgb(103, 58, 183));
                btn_cc_expense.setTextColor(Color.rgb(0, 0, 0));
                btn_cc_income.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_cc_expense.setBackground(null);

            }
        });
        btn_cc_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curChartView = CurChartView.expenseChart;
                InitCircleChart();

                btn_cc_expense.setTextColor(Color.rgb(103, 58, 183));
                btn_cc_income.setTextColor(Color.rgb(0, 0, 0));
                btn_cc_expense.setBackgroundResource(R.drawable.btn_underline_blue);
                btn_cc_income.setBackground(null);
            }
        });
    }

    // 设置图表信息
    private void InitCircleChart() {
        ColorUtils.Instance().ResetIndex();
        CCConfig config = new CCConfig();
        if (mList != null) {
            ListViewItems temp;
            if (curChartView == CurChartView.incomeChart)
                for (int i = 0; i < mList.size(); ++i) {
                    temp = mList.get(i);
                    if (temp.getTotal() > 0)
                        config.addData(new CCInfo(temp.getTotal(), ColorUtils.Instance().GetColorR(), temp.getDate()));
                }
            else if (curChartView == CurChartView.expenseChart) {
                for (int i = 0; i < mList.size(); ++i) {
                    temp = mList.get(i);
                    if (temp.getTotal() < 0)
                        config.addData(new CCInfo(Math.abs(temp.getTotal()), ColorUtils.Instance().GetColorB(), temp.getDate()));
                }

            }
        }
        My_cc.applyConfig(config);
        My_cc.postInvalidate();
    }

    private void loadData() {
        int size = 0;
        for (int i = 0; i < mList.size(); i++) {
            size += mList.get(0).items.size();
        }
        mMyListView.setLoadCompleted(5 <= size);
        mMyLvAdapter.notifyDataSetChanged();
    }

    /**
     * 获取指定年、月的数据(更新mList)
     *
     * @param year
     * @param month
     */
    @SuppressLint("DefaultLocale")
    private void GetMonthData(int year, int month) {
        DAOData daoData = new DAOData(mContext);
        mList.clear();
        mList.addAll(ValuesTransform.transform_ListData_To_ListViewItems(daoData.queryByMonth(year, month - 1)));
        // 统计该月总支出 总收入
        int curMonthTotalIncome = 0;
        int curMonthTotalExpense = 0;
        for (int i = 0; i < mList.size(); i++) {
            ListViewItems temp_lv = mList.get(i);
            int temp_total = temp_lv.getTotal();
            if (temp_total > 0)
                curMonthTotalIncome += temp_total;
            else
                curMonthTotalExpense += temp_total;
        }
        t_curMonthTotalIncome.setText(String.format("￥ +%d", curMonthTotalIncome));
        t_curMonthTotalExpense.setText(String.format("￥ %d", curMonthTotalExpense));
        setNoDataSign();
        mMyLvAdapter.notifyDataSetChanged();
    }

    /**
     * 获取当前月份的数据 (更新mList)
     */
    private void GetCurMonthData() {
        GetMonthData(curYear, curMonth);
    }

    /**
     * 控制 月份 显示的控件
     *
     * @param month
     */
    @SuppressLint("StringFormatMatches")
    private void setCurSelectMonth(int month) {
        if (t_curMonth == null)
            return;
        if (month > 1 && month < curMonth) {
            btn_next.setVisibility(View.VISIBLE);
            btn_last.setVisibility(View.VISIBLE);
        } else if (month <= 1) {
            btn_last.setVisibility(View.INVISIBLE);
            btn_next.setVisibility(View.VISIBLE);
        } else if (month >= curMonth) {
            btn_next.setVisibility(View.INVISIBLE);
            btn_last.setVisibility(View.VISIBLE);
        }
        curSelectMonth = month;
        if (curMonth == curSelectMonth) {
            t_curMonth.setText(getString(R.string.app_month, "本"));
        } else {
            t_curMonth.setText(getString(R.string.app_month, curSelectMonth));
        }
    }

    /**
     * 设置“无数据提示控件”的状态
     */
    private void setNoDataSign() {
        if (mList.size() > 0)
            view_noData.setVisibility(View.GONE);
        else
            view_noData.setVisibility(View.VISIBLE);
    }


    /**
     * 上个月按钮的点击事件
     */
    private void bindLastBtn() {
        setCurSelectMonth(curSelectMonth - 1);
        GetMonthData(curYear, curSelectMonth);
        InitCircleChart();
    }

    /**
     * 下个月按钮的点击事件
     */
    private void bindNextBtn() {
        setCurSelectMonth(curSelectMonth + 1);
        GetMonthData(curYear, curSelectMonth);
        InitCircleChart();
    }
}