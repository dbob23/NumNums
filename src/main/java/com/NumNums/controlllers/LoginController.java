package com.NumNums.controlllers;


import com.NumNums.models.User;
import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


    @Controller
    public class LoginController {

        @Autowired
        private UserService userService;

        @RequestMapping(value = "NumNums/login", method = RequestMethod.GET)
        public ModelAndView login() {
            ModelAndView modelAndView = new ModelAndView();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            if (user != null) {
                modelAndView.addObject("user", "Welcome,  " + user.getUsername());
                modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
                modelAndView.setViewName("admin/home");
            } else {

                modelAndView.addObject("title", "Log In");
                modelAndView.setViewName("login/login");
            }

            return modelAndView;
        }
    }






