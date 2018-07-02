package com.example.hp.offermagnet;

/**
 * Created by hp on 14/04/2018.
 */

public class DataItemRequest {
    public String title, desc, dateTo, imageUrl, productImageUrl, id, phone;
    private String user_name;
    private String user_id;
    private String city;
    private String cat_id;



    public DataItemRequest(String id, String title, String desc, String imageUrl, String dateTo,String cat_id, String productImageUrl, String phone, String user_name,String user_id,String city) {
        this.id = id;
        this.title = title;
        this.phone = phone;
        this.user_id=user_id;
        this.user_name=user_name;
        this.city=city;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.productImageUrl = productImageUrl;
        this.setCat_id(cat_id);
        this.dateTo = dateTo;


    }

    public String getPhone() {
        return phone;
    }



    public DataItemRequest(String title, String desc, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
