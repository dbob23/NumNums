package com.NumNums.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SearchDetails {

    @NotNull(message = "Please enter a valid zip code.")
    @NotBlank(message = "Please enter a valid zip code.")
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "You entered an invalid zip code.")
    private String zipCode;

    public SearchDetails() {
    }

    public SearchDetails(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

}

