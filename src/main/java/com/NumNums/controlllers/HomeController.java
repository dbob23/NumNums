package com.NumNums.controlllers;


import com.NumNums.models.SearchDetails;
import com.NumNums.models.service.RestaurantService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;


@Controller
@RequestMapping("NumNums")
public class HomeController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@ModelAttribute("aSearch") @Valid SearchDetails aSearch, Errors errors, Model model) {
        model.addAttribute("aSearch", new SearchDetails());
        model.addAttribute("title", "NumNums!");
        return "home/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processZipCode(@ModelAttribute("aSearch") @Valid SearchDetails aSearch, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "NumNums!");
            return "home/index";
        }
        String zipCode = aSearch.getZipCode();
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?components=postal_code:" + zipCode + "|country:US&key=AIzaSyDvq8G2idSuiPwzYEt6JIsbqtP29RjZZ0c");
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext())
                str += scan.nextLine();
//            System.out.println(str);
            scan.close();
            JSONObject jb = new JSONObject(str);
            JSONArray results = (JSONArray) jb.get("results");
            JSONObject jsonObject1 = (JSONObject) results.get(0);
            JSONObject geometry = (JSONObject) jsonObject1.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            BigDecimal lat = location.getBigDecimal("lat");
            BigDecimal lng = location.getBigDecimal("lng");
            aSearch.setLatitude(lat);
            aSearch.setLongitude(lng);
            System.out.println("Lat = " + location.get("lat"));
            System.out.println("Lng = " + location.get("lng"));
        } catch (Exception e) {
        } finally {
            model.addAttribute("title", "NumNums! Display");

//            Set parameters for Query

            BigDecimal latitude = aSearch.getLatitude();
            BigDecimal longitude = aSearch.getLongitude();
            int distance = aSearch.getDistance();

            model.addAttribute("restaurants", RestaurantService.locateRestaurants(latitude, longitude, distance));

            System.out.println(model);

        }
        return "home/display";
    }
}