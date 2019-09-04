package com.NumNums.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

public class SearchDetails {

    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "You must enter a valid zipcode. Please try again.")
    private String zipCode;

    @NotNull
    @NotEmpty(message = "Please select at least one option.")
    private ArrayList choices;

    private int distance;

    public SearchDetails() {}

    public SearchDetails(String zipCode, int distance, ArrayList choices) {
        this.zipCode = zipCode;
        this.distance = distance;
        this.choices = choices;
    }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getZipCode() { return zipCode; }

    public int getDistance() { return distance; }

    public void setDistance(int distance) { this.distance = distance; }

    public ArrayList getChoices() {
        return choices;
    }

    public void setChoices(ArrayList choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "SearchDetails: " +
                "zipCode='" + zipCode + '\'' +
                ", choices=" + choices +
                ", distance=" + distance +
                '.';
    }


}

