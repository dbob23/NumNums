package com.NumNums.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchDetails {

    public SearchDetails() {
    }

    @NotNull
    @Size(min = 5, max = 5, message = "Please enter a valid Zip Code.")
    private String zipCode;


    public SearchDetails(String zipCode) {
        this.zipCode = zipCode;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode() {

    }
}

