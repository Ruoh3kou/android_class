package com.cbj.Utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorUtils {
    private static ColorUtils instance;

    public static synchronized ColorUtils Instance() {

        if (instance == null)
            instance = new ColorUtils();
        return instance;
    }

    private int curR;
    private int curB;

    private List<Integer> colorsR;

    private List<Integer> colorsB;

    public int GetColorR() {
        if (curR >= colorsR.size())
            colorsR.add(GetRandomColorR());
        return colorsR.get(curR++);
    }

    public int GetColorB() {
        if (curB >= colorsB.size())
            colorsB.add(GetRandomColorR());
        return colorsB.get(curB++);
    }

    public void ResetIndex() {
        curR = 0;
        curB = 0;
    }

    private ColorUtils() {
        curB = 0;
        curR = 0;
        colorsB = new ArrayList<>();
        colorsR = new ArrayList<>();

        colorsR.add(Color.parseColor("#e41749"));
        colorsR.add(Color.parseColor("#f5587b"));
        colorsR.add(Color.parseColor("#ff8a5c"));
        colorsR.add(Color.parseColor("#ffc5a1"));
        colorsR.add(Color.parseColor("#ffd19a"));
        colorsR.add(Color.parseColor("#ff4949"));
        colorsR.add(Color.parseColor("#dedede"));
        colorsR.add(Color.parseColor("#eeeeee"));

        colorsB.add(Color.parseColor("#5bd1d7"));
        colorsB.add(Color.parseColor("#348498"));
        colorsB.add(Color.parseColor("#004d61"));
        colorsB.add(Color.parseColor("#67eaca"));
        colorsB.add(Color.parseColor("#b0f4e6"));
        colorsB.add(Color.parseColor("#fcf9ec"));
        colorsB.add(Color.parseColor("#6fc2d0"));
        colorsB.add(Color.parseColor("#373a6d"));
    }

    public int GetRandomColorR() {
        Random random = new Random();
        int r = random.nextInt(255) + 100;
        int g = random.nextInt(70) + 10;
        int b = random.nextInt(60) + 10;
        return Color.rgb(r, g, b);
    }

    public int GetRandomColorB() {
        Random random = new Random();
        int r = random.nextInt(70) + 10;
        int g = random.nextInt(70) + 10;
        int b = random.nextInt(255) + 100;
        return Color.rgb(r, g, b);
    }
}
