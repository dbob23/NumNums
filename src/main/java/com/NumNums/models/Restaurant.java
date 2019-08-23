package com.NumNums.models;


import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "restaurantId")
    private int id;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "")
    @Column(name = "userId")
    private int userId;

    @Column(name = "name")
    @NotEmpty(message = "Name field may not be left empty.")
    private String restaurantName;

    @Column(name = "webAddress")
    @URL(message = "Please provide a valid web address.")
    @NotEmpty(message = "Please provide a valid web address.")
    private String webAddress;

    @Column(name = "streetAddress")
    @NotEmpty(message = "Please provide a street address.")
    private String streetAddress;

    @Column(name = "glutenFree")
    private boolean glutenFree;

    @Column(name = "lactoseFree")
    private boolean lactoseFree;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    @Column(name = "vegan")
    private boolean vegan;

    @Column(name = "nonVegetarian")
    private boolean nonVegetarian;

    public Restaurant() {}

    public Restaurant (Integer id, Integer userId, String restaurantName, String webAddress, String streetAddress){
        this.id = id;
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.webAddress = webAddress;
        this.streetAddress = streetAddress;
        this.glutenFree = false;
        this.lactoseFree = false;
        this.vegan = false;
        this.vegetarian = false;
        this.nonVegetarian = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isLactoseFree() {
        return lactoseFree;
    }

    public void setLactoseFree(boolean lactoseFree) {
        this.lactoseFree = lactoseFree;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isNonVegetarian() {
        return nonVegetarian;
    }

    public void setNonVegetarian(boolean nonVegetarian) {
        this.nonVegetarian = nonVegetarian;
    }
}
