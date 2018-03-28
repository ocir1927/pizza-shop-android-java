package com.costi.labmobile.model;

import java.util.List;

/**
 * Created by Costi  on 14.01.2018 . All rights reserved.
 */

public class Pizza {
    private Integer id;
    private String name;
    private List<String> contains;
    private float price;
    private int quantity;
    private String photoUrl;


    public Pizza() {
    }

    public Pizza(Integer id, String name, List<String> contains, float price, int quantity, String photoUrl) {
        this.id = id;
        this.name = name;
        this.contains = contains;
        this.price = price;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
    }

    public Pizza(String name, List<String> contains, float price, int quantity, String photoUrl) {
        this.name = name;
        this.contains = contains;
        this.price = price;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getContains() {
        return contains;
    }

    public void setContains(List<String> contains) {
        this.contains = contains;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contains=" + contains +
                ", price=" + price +
                ", quantity=" + quantity +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
