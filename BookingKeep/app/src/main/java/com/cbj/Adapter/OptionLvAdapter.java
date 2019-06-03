package com.cbj.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbj.Activity.ClassActivity;
import com.cbj.bookingkeep.R;

import java.util.List;

public class OptionLvAdapter extends BaseAdapter {

    private List<String> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public OptionLvAdapter(List<String> mList, Context context) {
        super();
        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final OptionLvAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_option_item, null);
            holder = new OptionLvAdapter.ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.list_option_item_text);
            convertView.setTag(holder);
        } else {
            holder = (OptionLvAdapter.ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(mList.get(position));
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
    }
}
