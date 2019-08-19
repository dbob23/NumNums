package com.NumNums.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutController {


    @RequestMapping(value="NumNums/about", method = RequestMethod.GET)
    public String displayAboutPage(Model model) {
        model.addAttribute("title", "About NumNums");
        return "about/about";
    }
}
