package com.example.hp.offermagnet;

/**
 * Created by hp on 30/06/2018.
 */

public class DataItemOfRequest {
   private String id;
    private String offer_user;
    private String req_id;
    private String offer_Desc;
    private String req_desc;
    private String user_name;
    private String phone;
    private String profile_pic;
    private String Request_user;

    public  DataItemOfRequest(String id,String offer_user,String req_id,String offer_Desc
   ,String user_name,String phone,String profile_pic,String Request_user ){
        this.setId(id);
        this.setOffer_user(offer_user);
        this.setReq_id(req_id);
        this.setOffer_Desc(offer_Desc);
        this.setUser_name(user_name);
        this.setPhone(phone);
        this.setProfile_pic(profile_pic);
        this.setRequest_user(Request_user);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffer_user() {
        return offer_user;
    }

    public void setOffer_user(String offer_user) {
        this.offer_user = offer_user;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getOffer_Desc() {
        return offer_Desc;
    }

    public void setOffer_Desc(String offer_Desc) {
        this.offer_Desc = offer_Desc;
    }

    public String getReq_desc() {
        return req_desc;
    }

    public void setReq_desc(String req_desc) {
        this.req_desc = req_desc;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getRequest_user() {
        return Request_user;
    }

    public void setRequest_user(String request_user) {
        Request_user = request_user;
    }
}
