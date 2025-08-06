package com.example.Payment.model;

import org.springframework.data.annotation.Id;

public class Purchased {
    private String id;
    private String item;
    private int quantity;
    @Id
    private int purchased_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPurchased_id() {
        return purchased_id;
    }

    public void setPurchased_id(int purchased_id) {
        this.purchased_id = purchased_id;
    }
}
