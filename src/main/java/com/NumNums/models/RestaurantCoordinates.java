package com.NumNums.models;

import java.util.List;

public class RestaurantCoordinates {

    private List<Double> coordinates;

    public RestaurantCoordinates(){};

    public RestaurantCoordinates(List<Double> coordinates){
        this.coordinates = coordinates;
    }


    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
