package com.NumNums.controlllers;


import com.NumNums.models.User;
import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
public class RegistrationController {


    @Autowired
    private UserService userService;



    @RequestMapping(value="NumNums/registration", method = RequestMethod.GET)
        public String displayRegistrationForm(Model model) {
            model.addAttribute("title", "NumNums! Register");
            model.addAttribute(new User());
            return "login/registration";
        }

    @RequestMapping(value="NumNums/registration", method = RequestMethod.POST)
    public String processRegisterationForm(@ModelAttribute("user") @Valid User user, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "NumNums! Register");
            return "login/registration";
        }

        if ( ! user.getVerifyPassword().equals(user.getPassword()) ) {
            model.addAttribute("title", "NumNums! Register");
            model.addAttribute("verifyError", "Passwords must match.");
            return "login/registration";
        }

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            model.addAttribute("title", "NumNums! Register");
            model.addAttribute("error", "There is already a user with the email provided.");
            return "login/registration";
        }

        userService.saveUser(user);
        model.addAttribute("title", "NumNums! Log In");
        model.addAttribute("user", user);
        model.addAttribute("registeredMessage", "You have successfully created a new user! Please log in.");
        return "login/login";
    }
}