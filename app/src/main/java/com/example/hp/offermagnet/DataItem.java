package com.example.hp.offermagnet;

/**
 * Created by hp on 14/04/2018.
 */

public class DataItem {
    public String title, desc, dateFrom, dateTo, imageUrl, productImageUrl, id, phone;
    public String  likes, people;
    public String price;
    int rate;
    private String user_name;

    public DataItem(String id, String title, String desc, String imageUrl, String dateFrom, String dateTo, String price, String likes, int rate, String productImageUrl, String phone, String people) {
        this.id = id;
        this.title = title;
        this.phone = phone;
        this.people = people;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.productImageUrl = productImageUrl;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.likes = likes;
        this.rate = rate;

    }
    public DataItem(String id, String title, String desc, String imageUrl, String dateFrom, String dateTo, String price, String likes, int rate, String productImageUrl, String phone, String people,String user_name) {
        this.id = id;
        this.title = title;
        this.phone = phone;
        this.people = people;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.productImageUrl = productImageUrl;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.likes = likes;
        this.rate = rate;
        this.setUser_name(user_name);
    }


    public String getPhone() {
        return phone;
    }

    public String getPeople() {
        return people;
    }

    public DataItem(String title, String desc, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;

    }

    public String getDateFrom() {
        return dateFrom;
    }

    public int getRate() {
        return rate;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLikes() {
        return likes;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
