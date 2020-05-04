package com.taffyosales.taffyosales.model;

public class Users {
    String name;
    String email_id;
    String user_id;
    String password,user_image;

    public Users() {
    }


    public Users(String name, String email_id, String user_id, String password) {
        this.name = name;
        this.email_id = email_id;
        this.user_id = user_id;
        this.password = password;
    }

    public Users(String name, String email_id, String user_id, String password, String user_image) {
        this.name = name;
        this.email_id = email_id;
        this.user_id = user_id;
        this.password = password;
        this.user_image = user_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
