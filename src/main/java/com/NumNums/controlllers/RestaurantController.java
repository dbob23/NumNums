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
import org.springframework.web.bind.annotation.RequestParam;
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
        } else {
            restaurant.setUser(user);
            user.addRestaurant(restaurant);
            restaurantService.saveRestaurant(restaurant);
            modelAndView.addObject("restaurants", user.getRestaurants());
            modelAndView.addObject("message", "A restaurant has been registered successfully");
            modelAndView.setViewName("admin/home");
        }
            return modelAndView;
    }

    @RequestMapping(value = "NumNums/edit{id}", method = RequestMethod.GET)
    public ModelAndView displayEditForm(@RequestParam (value = "id", required = false) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("restaurant", restaurantService.findRestaurantById(id));
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername() + "!");
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
        modelAndView.addObject("user", user);
        Restaurant restaurantExists = restaurantService.findRestaurantByWebAddress(restaurant.getWebAddress().toLowerCase());

        if (restaurantExists != null && restaurant.getId() != restaurantExists.getId()) {
            modelAndView.addObject("restaurantExistsMessage", "That web address already exists in our database.");
            modelAndView.setViewName("admin/edit");
        } else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/edit");
        } else {
            restaurant.setUser(user);
            user.addRestaurant(restaurant);
            restaurantService.saveRestaurant(restaurant);
            modelAndView.addObject("restaurants", user.getRestaurants());
            modelAndView.addObject("message", "A restaurant has been edited successfully");
            modelAndView.addObject("id", id);
            modelAndView.setViewName("admin/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/delete{id}", method = RequestMethod.GET)
    public ModelAndView displayDeleteForm(@RequestParam (value = "id", required = false) int id ){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("restaurant", restaurantService.findRestaurantById(id));
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername() + "!");
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
        modelAndView.addObject("user", user);
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("message", "A restaurant has been deleted successfully");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}