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

    //    @RequestMapping(value="NumNums/add", method = RequestMethod.POST)
//    public ModelAndView
    @RequestMapping(value = "NumNums/add", method = RequestMethod.POST)
    public ModelAndView addNewRestaurant(@Valid Restaurant restaurant, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Restaurant restaurantExists = restaurantService.findRestaurantById(restaurant.getId());
        if (restaurantExists != null) {
            bindingResult
                    .rejectValue("restaurantName", "error.restaurant",
                            "There is already a restaurant registered with the information provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/add");
        } else {
            restaurantService.saveRestaurant(restaurant);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            modelAndView.addObject("message", "A restaurant has been registered successfully");
            modelAndView.addObject("user", user);
            modelAndView.setViewName("admin/home");

        }
        return modelAndView;
    }
}