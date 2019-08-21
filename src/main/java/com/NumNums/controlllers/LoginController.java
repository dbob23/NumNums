package com.NumNums.controlllers;


import com.NumNums.models.LogInUserObject;
import com.NumNums.models.User;
import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= "NumNums/login", method = RequestMethod.GET)
    public String displayLoginForm(@ModelAttribute("aLogInUserObject") LogInUserObject aLogInUserObject, Model model) {
        model.addAttribute("aLoginUserObject", new LogInUserObject());
        model.addAttribute("title", "Log In");
        return "login/login";
    }

    @RequestMapping(value = "NumNums/login", method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute("aLogInUserObject") LogInUserObject aLogInUserObject, Model model) {

        model.addAttribute("aLogInUserObject", new LogInUserObject());
        User aUser = userService.findUserByEmail(aLogInUserObject.getEmail());

        if (aUser == null){
            model.addAttribute("message", "You have entered an invalid email or password.");
            return "login/login";
        }
        if ( !aUser.getPassword().equals(aLogInUserObject.getPassword())){
            model.addAttribute("message", "You have entered an invalid email or password.");
            return "login/login";
        }

        aUser.setActive(2);
        model.addAttribute("title", "Welcome");
        model.addAttribute("user", aUser);
        model.addAttribute("message", "You are successfully logged in!");
        return "home/display";
       }

//       tutorial controller

//    @RequestMapping(value="NumNums/login", method = RequestMethod.GET)
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(auth.getName());
//        modelAndView.addObject("userName", "Welcome " + user.getUsername() + "  (" + user.getEmail() + ")");
//        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
//        modelAndView.setViewName("admin/home");
//        return modelAndView;
//    }


}



