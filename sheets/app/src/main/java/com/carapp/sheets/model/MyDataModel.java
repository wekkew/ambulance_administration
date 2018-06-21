package com.carapp.sheets.model;

public class MyDataModel {

    private String time;
    private String name;
    private int value;

    public String getTime() { return time; }
    public void setTime(String timeStamp) {
        this.time = timeStamp;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public int getValue() { return value; }
    public void setValue(int value) {
        this.value = value;
    }
}