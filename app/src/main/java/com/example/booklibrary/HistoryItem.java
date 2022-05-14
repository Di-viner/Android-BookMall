package com.example.booklibrary;

public class HistoryItem {
    String orderID;
    String name;
    String num;
    String totalPrice;
    String time;
    Integer pic;


    public HistoryItem(String orderID, String name, String num, String totalPrice, String time, Integer pic){
        this.orderID = orderID;
        this.name = name;
        this.num = num;
        this.totalPrice = totalPrice;
        this.time = time;
        this.pic = pic;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getName(){
        return name;
    }

    public String getNum(){
        return num;
    }

    public String getTotalPrice(){
        return totalPrice;
    }

    public String getTime(){
        return time;
    }

    public int getPic(){return pic;}
}
