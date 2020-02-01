package com.NumNums.models.service;


import com.NumNums.models.Restaurant;
import com.NumNums.models.data.RestaurantRepository;
import com.NumNums.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("restaurantService")
public class RestaurantService {

    private UserRepository userRepository;
    private static RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public static ArrayList findAll() {
        return (ArrayList) restaurantRepository.findAll();
    }

    public  static Restaurant findRestaurantById(int id) {

        return restaurantRepository.findById(id);
    }

//    public static ArrayList<Restaurant> locateRestaurants(SearchDetails aSearch) {
//        return restaurantRepository.locateRestaurants(aSearch);
//    }


    public static ArrayList<Restaurant> locateRestaurants (String zip){
        return restaurantRepository.locateRestaurants(zip);
    }

    public Restaurant findRestaurantByWebAddress(String webAddress) {
        return restaurantRepository.findByWebAddress(webAddress);
    }

    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(int id) {
        restaurantRepository.deleteById(id);
    }


//    public  static ArrayList<Restaurant> locateRestaurants(SearchDetails searchDetails) {
//        ArrayList<Restaurant> results = new ArrayList<>();
//
//        try {
//            // create mysql database connection
//            String myDriver = "com.mysql.cj.jdbc.Driver";
//            String myUrl = "jdbc:mysql://localhost:8889/NumNums?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//            Class.forName(myDriver);
//            Connection conn = DriverManager.getConnection(myUrl, "NumNums", "LaunchCode2019");
//
//            // create java statement
//            Statement st = conn.createStatement();
//
//            // execute the query, and get a java resultset
//            ResultSet rs = st.executeQuery("SELECT\n" +
//                    "    restaurant_id, (\n" +
//                    "      3959 * acos (\n" +
//                    "      cos ( radians(38.5950619) )\n" +
//                    "      * cos( radians( latitude) )\n" +
//                    "      * cos( radians( longitude ) - radians( -90.2291565) )\n" +
//                    "      + sin ( radians(38.5950619) )\n" +
//                    "      * sin( radians( latitude ) )\n" +
//                    "    )\n" +
//                    ") AS distance\n" +
//                    "FROM restaurant\n" +
//                    "HAVING distance < 5\n" +
//                    "ORDER BY distance\n" +
//                    "LIMIT 0 , 20;");
//
//            // iterate through the java resultset
//            while (rs.next()) {
//                int id = rs.getInt("restaurant_id");
//
//                // print the results
//                System.out.println("restaurant id: " + id);
//                results.add(findRestaurantById(id));
//            }
//            st.close();
//        } catch (Exception e) {
//            System.err.println("Got an exception! ");
//            System.err.println(e.getMessage());
//        }
//        return results;
//    }
}