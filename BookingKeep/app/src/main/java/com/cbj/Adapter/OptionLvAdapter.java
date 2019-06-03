package com.cbj.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbj.Activity.OptionActivity;
import com.cbj.DataStruct.Classification;
import com.cbj.bookingkeep.R;

import java.util.List;

public class OptionLvAdapter extends BaseAdapter {

    private List<Classification> mList;
    private LayoutInflater mInflater;
    private OptionActivity mContext;

    public OptionLvAdapter(List<Classification> mList, OptionActivity context) {
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
        final Button btn_del = mContext.btn_del;
        final Button btn_edit = mContext.btn_edit;
        final Button btn_undo = mContext.btn_undo;
        final OptionLvAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_option_item, null);
            holder = new OptionLvAdapter.ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.list_option_item_text);
            holder.cb_select = convertView.findViewById(R.id.list_option_item_check);
            holder.rl_item = convertView.findViewById(R.id.list_option_item_rl);
            convertView.setTag(holder);
        } else {
            holder = (OptionLvAdapter.ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(mList.get(position).getEvent());

        if (mContext.isMultiple) {
            holder.cb_select.setVisibility(View.VISIBLE);
            if (mContext.selectList.get(position)) {
                holder.cb_select.setChecked(true);
            } else {
                holder.cb_select.setChecked(false);
            }
        } else {
            btn_del.setVisibility(View.INVISIBLE);
            btn_edit.setVisibility(View.INVISIBLE);
            btn_undo.setVisibility(View.INVISIBLE);
            holder.cb_select.setVisibility(View.INVISIBLE);
        }

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext.isMultiple) {
                    mContext.selectList.set(position, !mContext.selectList.get(position));
                    notifyDataSetChanged();
                }
            }
        });

        holder.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mContext.isMultiple = true;
                btn_del.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.VISIBLE);
                btn_undo.setVisibility(View.VISIBLE);
                mContext.btn_addNew.setVisibility(View.INVISIBLE);
                mContext.SetSelectList();
                notifyDataSetChanged();
                return false;
            }
        });

        // 等于一个时候才显示edit。
        if (mContext.isMultiple) {
            int nums = 0;
            for (int i = 0; i < mContext.selectList.size(); i++) {
                if (mContext.selectList.get(i))
                    nums++;
            }
            if (nums == 1)
                btn_edit.setVisibility(View.VISIBLE);
            else
                btn_edit.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private CheckBox cb_select;
        private RelativeLayout rl_item;
    }
}
