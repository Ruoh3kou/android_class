package com.cbj.MyView.ListView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListSubView extends ListView {
    public MyListSubView(Context context) {
        super(context);
    }

    public MyListSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}