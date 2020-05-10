package com.taffyosales.taffyosales.model;

import com.google.firebase.firestore.FieldValue;

public class ProductDetails {
    FieldValue lastTimestamp;
    String productName;
    String quantity;


    public FieldValue getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(FieldValue lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ProductDetails(FieldValue lastTimestamp, String productName, String quantity) {
        this.lastTimestamp = lastTimestamp;
        this.productName = productName;
        this.quantity = quantity;
    }
}
