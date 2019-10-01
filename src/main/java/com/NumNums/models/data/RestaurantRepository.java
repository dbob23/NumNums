package com.NumNums.models.data;

import com.NumNums.models.Restaurant;
import com.NumNums.models.SearchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository("restaurantRepository")
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findById(int id);

    Restaurant findByWebAddress(String webAddress);

    @Transactional
    @Query(value = "SELECT *, (3959 * acos( cos(radians(/*${aSearch.latitude}*/)) * cos(radians(latitude)) * cos(radians(longitude) - radians(/*${aSearch.longitude}*/)) + sin(radians/*$(aSearch.latitude)*/) * sin(radians(latitude))))AS distance FROM restaurant HAVING distance</*${aSearch.distance}*/ ORDER BY distance LIMIT 0, 20;",nativeQuery = true)
    ArrayList<Restaurant> locateRestaurants(@Param("aSearch") SearchDetails aSearch);
}

