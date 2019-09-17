package com.NumNums.models;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


public class SearchDetails {

    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "You must enter a valid zipcode. Please try again.")
    private String zipCode;

    private int distance;

    private boolean glutenFree;

    private boolean lactoseFree;

    private boolean vegan;

    private boolean vegetarian;

    private boolean nonVegetarian;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public SearchDetails() {}

    public SearchDetails(String zipCode, int distance, boolean glutenFree, boolean lactoseFree, boolean vegan, boolean vegetarian, boolean nonVegetarian, BigDecimal latitude, BigDecimal longitude) {
        this.zipCode = zipCode;
        this.distance = distance;
        this.glutenFree = false;
        this.lactoseFree = false;
        this.vegan = false;
        this.vegetarian = false;
        this.nonVegetarian = false;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getZipCode() { return zipCode; }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }

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

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isNonVegetarian() {
        return nonVegetarian;
    }

    public void setNonVegetarian(boolean nonVegetarian) {
        this.nonVegetarian = nonVegetarian;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {

        return "You have searched for a restaurant within " + distance + " miles of " + zipCode + ". Here are the results:";
    }

}

