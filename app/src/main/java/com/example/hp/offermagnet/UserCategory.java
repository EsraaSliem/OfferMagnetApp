package com.example.hp.offermagnet;

/**
 * Created by nesma on 6/12/2018.
 */

public class UserCategory {

    UserCategory(int id,String name){
        this.id=id;
        this.name=name;
    }

    private int id ;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
