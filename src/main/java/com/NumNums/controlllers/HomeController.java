package com.NumNums.controlllers;


import com.NumNums.models.SearchDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("NumNums")
public class HomeController {

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model){

        model.addAttribute("title", "NumNums!");
        return "home/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String processZipCode(@ModelAttribute @Valid SearchDetails aSearch, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "NumNums!");
            return "home/index";
        }


        model.addAttribute("title", "NumNums!");
        model.addAttribute( "SearchDetails", aSearch);

//        test

        return "home/test";
    }

    }