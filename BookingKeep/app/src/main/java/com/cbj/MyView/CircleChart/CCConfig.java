package com.cbj.MyView.CircleChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CCConfig implements Serializable {

    private List<CCInfoImpl> mCCInfos;
    private AnimatedCCViewHelper mCCViewHelper;
    private int strokeWidth = 80;
    private float startAngle = -90.0f;

    public CCConfig() {
        mCCInfos = new ArrayList<>();
        mCCViewHelper = new AnimatedCCViewHelper();
    }

    public CCConfig addData(ICCInfo info) {
        if (mCCInfos == null) mCCInfos = new ArrayList<>();
        mCCInfos.add(CCInfoImpl.create(info));
        mCCViewHelper.prepare();
        return this;
    }

    public List<CCInfoImpl> getCCinfos() {
        return mCCInfos;
    }

    protected final class AnimatedCCViewHelper {
        private double sumValue;

        private void prepare() {
            //计算角度
            if (mCCInfos.isEmpty()) return;
            sumValue = 0;
            //算总和
            for (CCInfoImpl dataImpl : mCCInfos) {
                ICCInfo info = dataImpl.getCCInfo();
                sumValue += info.getValue();
            }
            //算每部分的角度
            float start = 0;
            for (CCInfoImpl data : mCCInfos) {
                data.setStartAngle(start);
                float angle = (float) (360.0 * (data.getCCInfo().getValue() / sumValue));
                angle = Math.max(1.0f, angle);
                float endAngle = start + angle;
                data.setEndAngle(endAngle);
                start = endAngle;
            }
        }

        public double getSumValue() {
            return sumValue;
        }
    }

}