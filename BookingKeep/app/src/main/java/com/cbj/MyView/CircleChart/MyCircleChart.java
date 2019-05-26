package com.cbj.MyView.CircleChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class MyCircleChart extends View {
    protected final String TAG = this.getClass().getSimpleName();
    private List<CCInfoImpl> mCurrentInfos;
    private CCConfig ccConfig;
    RectF mDrawRectF = new RectF();

    public MyCircleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //初始化
    private void init(Context context, AttributeSet attrs) {
//        mCurrentInfo = new CCInfoImpl();
    }

    public void applyConfig(CCConfig config) {
        ccConfig = config;
        mCurrentInfos = ccConfig.getCCinfos();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float width = getWidth() - getPaddingLeft() - getPaddingRight();
        final float height = getHeight() - getPaddingTop() - getPaddingBottom();

        canvas.translate(width / 2, height / 3);
        // 半径
        final float radius = (float) (Math.min(width, height) / 4);
        mDrawRectF.set(-radius, -radius, radius, radius);
        // 有数据再画
        if (mCurrentInfos != null) {
            for (int i = 0; i < mCurrentInfos.size(); i++) {
                CCInfoImpl mCurrentInfo = mCurrentInfos.get(i);
                canvas.drawArc(mDrawRectF, mCurrentInfo.getStartAngle(), 360 - mCurrentInfo.getStartAngle(), false, mCurrentInfo.getPaint());
            }
        }
    }

}
