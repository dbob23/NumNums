package com.NumNums.controlllers;


import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= "NumNums/login", method = RequestMethod.GET)
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Log In");
        return "login/login";
    }
}
