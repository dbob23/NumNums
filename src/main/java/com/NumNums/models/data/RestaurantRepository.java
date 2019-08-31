package com.NumNums.models.data;

import com.NumNums.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository("restaurantRepository")
//public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
//    Restaurant findById( int id);
//
//}

@Repository("restaurantRepository")
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    Restaurant findByWebAddress(String webAddress);
}