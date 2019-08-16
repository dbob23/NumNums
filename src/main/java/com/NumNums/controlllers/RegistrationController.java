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

//    @RequestMapping(value="NumNums/registration", method = RequestMethod.GET)
//    public String displayRegisterationForm(@ModelAttribute("user") @Valid User user, Errors errors, Model model) {
//        if (errors.hasErrors()) {
//            model.addAttribute("title", "NumNums!");
//            return "login/registration";
//        }
//
//
//
//        model.addAttribute("title", "Welcome");
//        model.addAttribute("user", new User());
//        return "login/welcome";
//    }

        @RequestMapping(value="NumNums/registration", method = RequestMethod.GET)
        public String displayRegistrationForm(Model model) {
            model.addAttribute("title", "NumNums!");
            model.addAttribute(new User());
            return "login/registration";

        }
}


