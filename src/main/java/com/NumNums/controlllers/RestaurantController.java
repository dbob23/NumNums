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
        modelAndView.addObject("title", "Add");
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
            modelAndView.addObject("title", "Add");
            modelAndView.setViewName("admin/add");
        } else if (bindingResult.hasErrors()) {
            modelAndView.addObject("title", "Add");
            modelAndView.setViewName("admin/add");
        } else {
            restaurant.setUser(user);
            modelAndView.addObject("restaurant", restaurant);
            modelAndView.addObject("message", "This restaurant will be added to NumNums!");
            modelAndView.addObject("title", "Confirm Add");
            modelAndView.setViewName("admin/confirmAdd");
        }
        return modelAndView;
    }


    @RequestMapping(value = "NumNums/confirmAdd", method = RequestMethod.GET)
    public ModelAndView displayConfirmAddForm(Restaurant restaurant) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject(new Restaurant());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "Confirm Add");
        modelAndView.addObject("restaurant", restaurant);
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername());
        modelAndView.addObject("message", "This restaurant will be added to NumNums!");
        modelAndView.setViewName("admin/confirmAdd");
        return modelAndView;
    }


    @RequestMapping(value = "NumNums/confirmAdd", method = RequestMethod.POST)
    public ModelAndView processConfirmAddForm(Restaurant restaurant) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
//        int editRestaurantId = restaurant.getId();
//        Restaurant editRestaurant = restaurantService.findRestaurantById(editRestaurantId);
//        if (editRestaurant != null) {
//            restaurant.setId(editRestaurantId);
//            restaurantService.saveRestaurant(restaurant);
//            modelAndView.addObject("restaurants", user.getRestaurants());
//            modelAndView.addObject("button", "Update this restaurant");
//            modelAndView.addObject("welcome", "Welcome, " + user.getUsername() + "!");
//            modelAndView.addObject("message", restaurant.getRestaurantName() + " has been updated in NumNums!");
//            modelAndView.addObject("title", "Logged In");
//            modelAndView.setViewName("admin/home");
//            return modelAndView;
//        }
        user.addRestaurant(restaurant);
        restaurant.setUser(user);
        restaurantService.saveRestaurant(restaurant);
        modelAndView.addObject("restaurants", user.getRestaurants());
        modelAndView.addObject("button", "Add this restaurant");
        modelAndView.addObject("welcome", "Welcome, " + user.getUsername() + "!");
        modelAndView.addObject("message", restaurant.getRestaurantName() + " has been added to NumNums!");
        modelAndView.addObject("title", "Logged In");
        modelAndView.setViewName("admin/home");
        return modelAndView;
       }

    @RequestMapping(value = "NumNums/edit{id}", method = RequestMethod.GET)
    public ModelAndView displayEditForm(@RequestParam (value = "id", required = false) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "Edit");
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
            modelAndView.addObject("title", "Edit");
            modelAndView.addObject("restaurantExistsMessage", "That web address already exists in our database.");
            modelAndView.setViewName("admin/edit");
        } else if (bindingResult.hasErrors()) {
            modelAndView.addObject("title", "Edit");
            modelAndView.setViewName("admin/edit");
        } else {
            restaurant.setUser(user);
            user.addRestaurant(restaurant);
            restaurantService.saveRestaurant(restaurant);
            modelAndView.addObject("restaurants", user.getRestaurants());
            modelAndView.addObject("user", "Welcome, " + user.getUsername());
            modelAndView.addObject("message",  restaurant.getRestaurantName()+" has been edited successfully");
            modelAndView.addObject("id", id);
            modelAndView.addObject("title", "Logged In");
            modelAndView.setViewName("admin/home");
//            modelAndView.addObject("restaurant", restaurant);
//            modelAndView.setViewName("admin/confirmAdd");
        }
        return modelAndView;
    }

    @RequestMapping(value = "NumNums/delete{id}", method = RequestMethod.GET)
    public ModelAndView displayDeleteForm(@RequestParam (value = "id", required = false) int id ){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("title", "Delete");
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
        modelAndView.addObject("title", "Logged In");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}