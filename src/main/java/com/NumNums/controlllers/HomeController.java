package com.NumNums.controlllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//Hello, Jose!


@Controller
@RequestMapping("NumNums")
public class HomeController {

    @RequestMapping(value="")
    public String index(Model model){

        model.addAttribute("title", "NumNums!");
        return "home/index";
    }


}
