package com.NumNums.controlllers;

import com.NumNums.models.Restaurant;
import com.NumNums.models.User;
import com.NumNums.models.service.RestaurantService;
import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
        modelAndView.addObject("user", user);
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("message", "Welcome, " + user.getUsername() + "!");
        modelAndView.setViewName("admin/add");

        return modelAndView;

    }


    @RequestMapping(value = "NumNums/add", method = RequestMethod.POST)
    public ModelAndView addNewRestaurant(@Valid Restaurant restaurant, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        Restaurant restaurantExists = restaurantService.findRestaurantByWebAddress(restaurant.getWebAddress().toLowerCase());

        if (restaurantExists != null) {
            modelAndView.addObject("restaurantExistsMessage", "That restaurant already exists in our database.");
            modelAndView.setViewName("admin/add");
        } else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/add");
        }
        restaurant.setUser(user);
        user.addRestaurant(restaurant);
        restaurantService.saveRestaurant(restaurant);
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("message", "A restaurant has been registered successfully");
        modelAndView.setViewName("admin/home");

        return modelAndView;
    }
}
