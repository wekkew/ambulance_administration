package com.carapp.sheets.model;

public class MyDataModel {

    private int number;
    private String name;

    public String getNumber() { return ( Integer.toString(number) ); }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}