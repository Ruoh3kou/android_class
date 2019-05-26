package com.cbj.MyView.CircleChart;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.UUID;

final class CCInfoImpl {
    private final String id;
    private final ICCInfo mICCInfo;
    private float startAngle;
    private float endAngle;

    //调味料
    private Paint mPaint;
    private Path mLinePath;

    public static CCInfoImpl create(ICCInfo info) {
        return new CCInfoImpl(info);
    }

    private CCInfoImpl(ICCInfo info) {
        id = UUID.randomUUID().toString();
        this.mICCInfo = info;
        initPaint(info);
    }

    private void initPaint(ICCInfo info) {
        if (mPaint == null) mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if (mLinePath == null) mLinePath = new Path();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(60);
        mPaint.setColor(info.getColor());
    }

    public ICCInfo getCCInfo() {
        return mICCInfo;
    }

    public void setStartAngle(float start) {
        startAngle = start;
    }

    public void setEndAngle(float end) {
        endAngle = end;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public Paint getPaint() {
        return mPaint;
    }
}