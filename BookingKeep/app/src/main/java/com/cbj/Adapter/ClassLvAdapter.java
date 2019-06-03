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

public class ClassLvAdapter extends BaseAdapter {

    private List<String> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public ClassLvAdapter(List<String> mList, Context context) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_class_item, null);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.list_class_item_text);
            holder.rl_item = convertView.findViewById(R.id.list_class_item_rl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(mList.get(position));
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra("event", holder.tv_name.getText().toString());
                    ((ClassActivity) mContext).setResult(((ClassActivity) mContext).curState, intent);
                    ((ClassActivity) mContext).finish();
                } catch (Exception e) {
                    Log.e("跳转错误", e.toString());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private RelativeLayout rl_item;
    }
}
