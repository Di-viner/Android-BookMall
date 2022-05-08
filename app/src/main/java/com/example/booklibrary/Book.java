package com.example.booklibrary;

public class Book {
    private String name;
    private String price;
    private String intro;
    private Integer pic;

    public Book(){}
    public Book(String name, String price, String intro, Integer pic){
        this.name = name;
        this.price = price;
        this.intro = intro;
        this.pic = pic;
    }

    public String getName(){
        return this.name;
    }
    public String getPrice(){
        return this.price;
    }
    public String getIntro(){
        return this.intro;
    }
    public Integer getPic(){
        return this.pic;
    }
}
