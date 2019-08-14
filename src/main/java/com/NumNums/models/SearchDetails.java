package com.NumNums.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SearchDetails {

//    @NotNull(message = "Please enter a valid zip code.")
//    @NotBlank(message = "Zip code cannot be left blank")
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "You entered an invalid zip code. Please try again.")
    private String zipCode;


    private int distance;

    public SearchDetails() {
    }

    public SearchDetails(String zipCode, int distance) {
        this.zipCode = zipCode;
        this.distance = distance;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }

}

