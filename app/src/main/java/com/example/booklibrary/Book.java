package com.example.booklibrary;

public class Book {
    private String name;    //书名
    private String price;   //价格
    private String intro;   //书的分类
    private Integer pic;    //书的图片

    ///
    private String ID;      //书号
    private String author;  //作者
    private String content; //详细内容

    public Book(){}
    public Book(String name, String price, String intro, Integer pic, String ID, String author, String content){
        this.name = name;
        this.price = price;
        this.intro = intro;
        this.pic = pic;
        this.ID = ID;
        this.author = author;
        this.content = content;
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

    public String getID(){
        return this.ID;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getContent(){
        return this.content;
    }

}
