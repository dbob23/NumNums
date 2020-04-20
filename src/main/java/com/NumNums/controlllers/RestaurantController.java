package com.NumNums.controlllers;

import com.NumNums.models.Restaurant;
import com.NumNums.models.User;
import com.NumNums.models.service.RestaurantService;
import com.NumNums.models.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

@Controller
public class RestaurantController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "NumNums/add", method = RequestMethod.GET)
    public ModelAndView displayAddForm() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(new Restaurant());
        modelAndView.addObject("title", "NumNums! Add a Restaurant");
        modelAndView.addObject("message", "You are logged in!");
        modelAndView.addObject("user", "Welcome, " + user.getUsername());
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.setViewName("admin/add");
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/add", method = RequestMethod.POST)
    public ModelAndView addNewRestaurant(@Valid Restaurant restaurant, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", "Welcome, " + user.getUsername());

        Restaurant restaurantExists = restaurantService.findRestaurantByWebAddress(restaurant.getWebAddress().toLowerCase());

        if (restaurantExists != null) {
            modelAndView.addObject("restaurantExistsMessage", "That restaurant already exists in our database.");
            modelAndView.addObject("title", "NumNums! Add a Restaurant");
            modelAndView.setViewName("admin/add");
        } else if (bindingResult.hasErrors()) {
            modelAndView.addObject("title", "NumNums! Add a Restaurant");
            modelAndView.setViewName("admin/add");
        } else {
            restaurant.setUser(user);
            modelAndView.addObject("restaurant", restaurant);
            modelAndView.addObject("message", "This restaurant will be added to NumNums!");
            modelAndView.addObject("title", "NumNums! Add a Restaurant");
            modelAndView.setViewName("admin/confirmAdd");
        }
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/confirmAdd", method = RequestMethod.GET)
    public ModelAndView displayConfirmAddForm(Restaurant restaurant) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "NumNums! Add a Restaurant");
        modelAndView.addObject("restaurant", restaurant);
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername());
        modelAndView.addObject("message", "This restaurant will be added to NumNums!");
        modelAndView.setViewName("admin/confirmAdd");
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/confirmAdd", method = RequestMethod.POST)
    public ModelAndView processConfirmAddForm(Restaurant restaurant)  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("home", "/NumNums/login"  );
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        String address = restaurant.getStreetAddress() + "," + restaurant.getCity() + "," + restaurant.getState();
        String noSpaces = address.replace(" ", "+");

//      retrieve lat and long from geocode api and set it to the added restaurant

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + noSpaces + "&key=AIzaSyDvq8G2idSuiPwzYEt6JIsbqtP29RjZZ0c");
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext())
                str += scan.nextLine();
                System.out.println(str);
                scan.close();
                JSONObject jb = new JSONObject(str);
                JSONArray results = (JSONArray) jb.get("results");
                JSONObject jsonObject1 = (JSONObject) results.get(0);
                JSONObject geometry = (JSONObject) jsonObject1.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                BigDecimal lat = location.getBigDecimal("lat");
                BigDecimal lng = location.getBigDecimal("lng");
                restaurant.setLatitude(lat);
                restaurant.setLongitude(lng);
                System.out.println("Lat = " + location.get("lat"));
                System.out.println("Lng = " + location.get("lng"));
            }
        catch(Exception e) {
        }
        finally {
        user.addRestaurant(restaurant);
        restaurant.setUser(user);
        restaurantService.saveRestaurant(restaurant);
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("button", "Add this restaurant");
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername() + "!");
        modelAndView.addObject("message", restaurant.getRestaurantName() + " has been added to NumNums!");
        modelAndView.addObject("title", "Logged In");
        modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/edit{id}", method = RequestMethod.GET)
    public ModelAndView displayEditForm(@RequestParam (value = "id", required = false) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "NumNums! Edit a Restaurant");
        modelAndView.addObject("user","Welcome, " + user.getUsername());
        modelAndView.addObject("restaurant", restaurantService.findRestaurantById(id));
        modelAndView.addObject("message", "All fields will be saved as they appear when submitted.");
        modelAndView.addObject("id", id);
        modelAndView.setViewName("admin/edit");
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/edit{id}", method = RequestMethod.POST)
    public ModelAndView processEditForm(@Valid Restaurant restaurant, BindingResult bindingResult, @RequestParam (value = "id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Restaurant restaurantExists = restaurantService.findRestaurantByWebAddress(restaurant.getWebAddress().toLowerCase());
        if (restaurantExists != null && restaurant.getId() != restaurantExists.getId()) {
            modelAndView.addObject("title", "NumNums! Edit a Restaurant");
            modelAndView.addObject("restaurantExistsMessage", "That web address already exists in our database.");
            modelAndView.setViewName("admin/edit");
        } else if (bindingResult.hasErrors()) {
            modelAndView.addObject("title", "NumNums! Edit a Restaurant");
            modelAndView.setViewName("admin/edit");
        } else {
            restaurant.setUser(user);
            user.addRestaurant(restaurant);
            String address = restaurant.getStreetAddress() + "," + restaurant.getCity() + "," + restaurant.getState();
            String noSpaces = address.replace(" ", "+");
            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + noSpaces + "&key=AIzaSyDvq8G2idSuiPwzYEt6JIsbqtP29RjZZ0c");
                Scanner scan = new Scanner(url.openStream());
                String str = new String();
                while (scan.hasNext())
                    str += scan.nextLine();
                System.out.println(str);
                scan.close();
                JSONObject jb = new JSONObject(str);
                JSONArray results = (JSONArray) jb.get("results");
                JSONObject jsonObject1 = (JSONObject) results.get(0);
                JSONObject geometry = (JSONObject) jsonObject1.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                BigDecimal lat = location.getBigDecimal("lat");
                BigDecimal lng = location.getBigDecimal("lng");
                restaurant.setLatitude(lat);
                restaurant.setLongitude(lng);
                System.out.println("Lat = " + location.get("lat"));
                System.out.println("Lng = " + location.get("lng"));
            } catch (Exception e) {
            } finally {
                restaurantService.saveRestaurant(restaurant);
                modelAndView.addObject("restaurants", user.getRestaurants());
                modelAndView.addObject("user", "Welcome, " + user.getUsername());
                modelAndView.addObject("message", restaurant.getRestaurantName() + " has been edited successfully");
                modelAndView.addObject("id", id);
                modelAndView.addObject("title", "NumNums! Home");
                modelAndView.setViewName("admin/home");
            }
        }
            return modelAndView;
        }

    @RequestMapping(value = "NumNums/delete{id}", method = RequestMethod.GET)
    public ModelAndView displayDeleteForm(@RequestParam (value = "id", required = false) int id ){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "NumNums! Delete a Restaurant");
        modelAndView.addObject("user", "Welcome, " + user.getUsername());
        modelAndView.addObject("restaurant", restaurantService.findRestaurantById(id));
        modelAndView.addObject("message", "This restaurant will be deleted from NumNums!");
        modelAndView.addObject("id", id);
        modelAndView.setViewName("admin/delete");
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/delete{id}", method = RequestMethod.POST)
    public ModelAndView processDeleteForm(@RequestParam (value = "id", required = false) int id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        user.getRestaurants().remove(restaurantService.findRestaurantById(id));
        restaurantService.deleteRestaurant(id);
        modelAndView.addObject("user", "Welcome, " + user.getUsername());
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("message", "A restaurant has been deleted successfully");
        modelAndView.addObject("title", "NumNums! Home");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}