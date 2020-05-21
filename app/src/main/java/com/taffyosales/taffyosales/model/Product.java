package com.taffyosales.taffyosales.model;

import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;

public class Product {
    String Product_id;
    String Produc_name;
    String Product_type;
    String imageUri;
    int Quantity;
    int Selling_price;
    int Cost_price;

    FieldValue timestamp;

    public Product(String product_id, String produc_name, String product_type, String imageUri, int quantity, int selling_price, int cost_price, FieldValue timestamp) {
        Product_id = product_id;
        Produc_name = produc_name;
        Product_type = product_type;
        this.imageUri = imageUri;
        Quantity = quantity;
        Selling_price = selling_price;
        Cost_price = cost_price;
        this.timestamp = timestamp;
    }


    public String getProduct_type() {
        return Product_type;
    }

    public void setProduct_type(String product_type) {
        Product_type = product_type;
    }

    public Product(String product_id, String produc_name, int quantity, int selling_price, int cost_price, FieldValue timestamp) {
        Product_id = product_id;
        Produc_name = produc_name;
        this.imageUri = imageUri;
        Quantity = quantity;
        Selling_price = selling_price;
        Cost_price = cost_price;
        this.timestamp = timestamp;
    }

    public FieldValue getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(FieldValue timestamp) {
        this.timestamp = timestamp;
    }

    public Product() {
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getProduc_name() {
        return Produc_name;
    }

    public void setProduc_name(String produc_name) {
        Produc_name = produc_name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getSelling_price() {
        return Selling_price;
    }

    public void setSelling_price(int selling_price) {
        Selling_price = selling_price;
    }

    public int getCost_price() {
        return Cost_price;
    }

    public void setCost_price(int cost_price) {
        Cost_price = cost_price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Product(String product_id, String produc_name, String imageUri, int quantity, int selling_price, int cost_price) {
        Product_id = product_id;
        Produc_name = produc_name;
        this.imageUri = imageUri;
        Quantity = quantity;
        Selling_price = selling_price;
        Cost_price = cost_price;
    }
}
