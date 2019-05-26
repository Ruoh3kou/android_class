package com.cbj.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cbj.DataStruct.ListViewItems;
import com.cbj.bookingkeep.R;
import com.cbj.MyView.ListView.MyListSubView;
import com.cbj.DataStruct.Data;

import java.util.ArrayList;
import java.util.List;

public class MyLvAdapter extends BaseAdapter {
    private List<ListViewItems> mDataList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyLvAdapter(List<ListViewItems> mList, Context context) {
        super();
        this.mDataList = mList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.tv_date = convertView.findViewById(R.id.lv_item_date);
            holder.tv_total = convertView.findViewById(R.id.lv_item_total);
            holder.lv_sub = convertView.findViewById(R.id.lv_sub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_date.setText(mDataList.get(position).getDate());

        // 支出多黑色，收入多红色
        int total = mDataList.get(position).getTotal();
        if (total < 0) {
            holder.tv_total.setTextColor(Color.rgb(0, 0, 0));
            holder.tv_total.setText(total + "");

        } else {
            holder.tv_total.setTextColor(Color.rgb(255, 0, 0));
            holder.tv_total.setText("+" + total);
        }

        List<Data> mSubList = new ArrayList<>();
        MySubLvAdapter subAdapter = new MySubLvAdapter(mSubList, mContext);
        holder.lv_sub.setAdapter(subAdapter);
        ArrayList<Data> tempItems = mDataList.get(position).items;
        for (int i = 0; i < tempItems.size(); i++) {
            Data temp = tempItems.get(i);
            mSubList.add(temp);
        }
        subAdapter.notifyDataSetChanged();
        return convertView;
    }

    class ViewHolder {
        private TextView tv_date;
        private TextView tv_total;
        private MyListSubView lv_sub;
    }
}