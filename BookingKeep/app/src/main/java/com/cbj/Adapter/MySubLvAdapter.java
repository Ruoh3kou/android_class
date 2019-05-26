package com.cbj.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cbj.bookingkeep.R;
import com.cbj.DataStruct.Data;

import java.util.List;

public class MySubLvAdapter extends BaseAdapter {

    private List<Data> mDataList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MySubLvAdapter(List<Data> mList, Context context) {
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
            convertView = mInflater.inflate(R.layout.list_subitem, null);
            holder = new ViewHolder();
            holder.tv_type = convertView.findViewById(R.id.lv_item_type);
            holder.tv_event = convertView.findViewById(R.id.lv_item_event);
            holder.tv_time = convertView.findViewById(R.id.lv_item_time);
            holder.tv_money = convertView.findViewById(R.id.lv_item_money);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_type.setText(mDataList.get(position).getType());
        holder.tv_event.setText(mDataList.get(position).getEvent());
        holder.tv_time.setText(mDataList.get(position).getTime());
        int money = mDataList.get(position).getMoney();

        // 支出黑色，收入红色
        if (money < 0) {
            holder.tv_money.setTextColor(Color.rgb(0, 0, 0));
            holder.tv_money.setText(money + "");
        } else {
            holder.tv_money.setTextColor(Color.rgb(255, 0, 0));
            holder.tv_money.setText("+" + money);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_type;
        private TextView tv_event;
        private TextView tv_money;
        private TextView tv_time;
    }
}
