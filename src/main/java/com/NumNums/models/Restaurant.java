package com.NumNums.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "restaurant_id")
    private int id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(name = "name")
    @NotEmpty(message = "Name field may not be left empty.")
    private String restaurantName;

    @Column(name = "webAddress")
    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\Q$-_+!*'(),%\\E]+\\.)+[‌​\\w]{2,63}\\/?$" , message = "Please enter a valid web address." )
    @NotEmpty(message = "Please provide a valid web address.")
    private String webAddress;

    @Column(name = "streetAddress")
    @NotEmpty(message = "Please provide a street address.")
    private String streetAddress;

    @Column(name = "city")
    @NotEmpty(message = "Please provide a city.")
    private String city;

    @Column(name = "state")
    @NotEmpty(message = "Please provide a state.")
    private String state;

    @Column(name = "zipCode")
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "You must enter a valid zipcode. Please try again.")
    private String zipCode;

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

    @Column(name = "latitude", precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    private BigDecimal longitude;

    public Restaurant() {}

    public Restaurant(int id, User user, String restaurantName, String webAddress, String streetAddress, String city, String state, String zipCode, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.user = user;
        this.webAddress = webAddress;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.glutenFree = false;
        this.lactoseFree = false;
        this.vegan = false;
        this.vegetarian = false;
        this.nonVegetarian = false;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public BigDecimal getLatitude() { return latitude; }

    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }

    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    @Override
    public String toString() {
        return "Restaurant" +
                "id=" + id +
                ", user=" + user +
                ", restaurantName='" + restaurantName + '\'' +
                ", webAddress='" + webAddress + '\'' ;
    }
}