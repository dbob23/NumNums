package com.NumNums.models.service;


import com.NumNums.models.Restaurant;
import com.NumNums.models.data.RestaurantRepository;
import com.NumNums.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("restaurantService")
public class RestaurantService {

    private UserRepository userRepository;
    private static RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository){
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public static ArrayList findAll() {
        return (ArrayList) restaurantRepository.findAll();
    }

    public Restaurant findRestaurantById (int id){

        return restaurantRepository.findById(id);
    }

    public Restaurant findRestaurantByWebAddress (String webAddress){
        return restaurantRepository.findByWebAddress(webAddress);
    }

    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(int id) {
        restaurantRepository.deleteById(id);
    }

}
