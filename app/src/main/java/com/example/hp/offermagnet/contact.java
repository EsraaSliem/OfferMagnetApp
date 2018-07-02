package com.example.hp.offermagnet;

/**
 * Created by hp on 30/10/2017.
 */

public class contact {
    private int id;
    private String name;
    private String phone;
   public contact(int id, String name ,String phone){
        this.setId(id);
        this.setName(name);
        this.setPhone(phone);
    }
    public contact(String name ,String phone){
        this.setName(name);
        this.setPhone(phone);
    }
    public void setId(int id){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
