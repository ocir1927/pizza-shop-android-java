package com.costi.labmobile.model;

import java.util.List;

/**
 * Created by Costi  on 15.01.2018 . All rights reserved.
 */

public class Order {
    private int id;
    private User user;
    private List<Pizza> pizzaList;

    public Order(User user, List<Pizza> pizzaList) {
        this.user = user;
        this.pizzaList = pizzaList;
    }

    public Order() {
    }

    public Order(int id, User user, List<Pizza> pizzaList) {
        this.id = id;
        this.user = user;
        this.pizzaList = pizzaList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }
}
