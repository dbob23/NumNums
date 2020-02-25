package com.NumNums.controlllers;

import com.NumNums.models.User;
import com.NumNums.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="NumNums/about", method = RequestMethod.GET)
    public String displayAboutPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (user!=null) {
            model.addAttribute("home", "/NumNums/login"  );

        }

        model.addAttribute("title", "About NumNums");
        return "about/about";
    }
}