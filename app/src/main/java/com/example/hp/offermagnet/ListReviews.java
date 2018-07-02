package com.example.hp.offermagnet;

/**
 * Created by ASUS on 21/03/2018.
 */

public class ListReviews {
     double rate ;
    String name;
    public  double getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }
    public ListReviews(double r, String d){
        this.rate = r;
        this.name = d;
    }
}
