package com.cbj.DataStruct;

import java.util.ArrayList;

public class ListViewItems {
    private String date;
    private int total;
    public ArrayList<Data> items;

    public ListViewItems() {
        date = "";
        total = 0;
        items = new ArrayList<>();
    }

    public void AddItems(Data it) {
        this.items.add(it);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

