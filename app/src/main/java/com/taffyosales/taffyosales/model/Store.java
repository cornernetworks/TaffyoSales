package com.taffyosales.taffyosales.model;

public class Store {

    String shopname,
            Area,
            Address,
            Pancard,
            Gst_no,
            Contact_no,
            email_id,
            user_id,
            city_name,
            Owner_name,
            Signature,
            store_id;

    String shop_type="Groceries";

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPancard() {
        return Pancard;
    }

    public void setPancard(String pancard) {
        Pancard = pancard;
    }

    public String getGst_no() {
        return Gst_no;
    }

    public void setGst_no(String gst_no) {
        Gst_no = gst_no;
    }

    public String getContact_no() {
        return Contact_no;
    }

    public void setContact_no(String contact_no) {
        Contact_no = contact_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getOwner_name() {
        return Owner_name;
    }

    public void setOwner_name(String owner_name) {
        Owner_name = owner_name;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public Store() {
    }

    public Store(String shopname, String area, String address, String pancard, String gst_no, String contact_no, String email_id, String user_id, String city_name, String owner_name, String signature, String store_id, String shop_type) {
        this.shopname = shopname;
        Area = area;
        Address = address;
        Pancard = pancard;
        Gst_no = gst_no;
        Contact_no = contact_no;
        this.email_id = email_id;
        this.user_id = user_id;
        this.city_name = city_name;
        Owner_name = owner_name;
        Signature = signature;
        this.store_id = store_id;
        this.shop_type = shop_type;
    }
}
