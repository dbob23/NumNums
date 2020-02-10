package com.NumNums.controlllers;


import com.NumNums.models.Restaurant;
import com.NumNums.models.SearchDetails;
import com.NumNums.models.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                        //build confirm message
        StringBuilder confirmMessage = new StringBuilder("You have searched for a restaurant within " + aSearch.getDistance() + " miles of zipcode " + aSearch.getZipCode());
        HashMap<String, Boolean> typeOfFood = new HashMap();
        ArrayList<String> onlyTrueTypes = new ArrayList<>();
        String zipCode = aSearch.getZipCode();

        typeOfFood.put("gluten-free", aSearch.isGlutenFree());
        typeOfFood.put("lactose-free", aSearch.isLactoseFree());
        typeOfFood.put("vegan", aSearch.isVegan());
        typeOfFood.put("vegetarian", aSearch.isVegetarian());
        typeOfFood.put("non-vegetarian", aSearch.isNonVegetarian());

        for ( Map.Entry<String, Boolean> type : typeOfFood.entrySet()) {
            if (type.getValue() == true) {
                onlyTrueTypes.add(type.getKey());
            }
        }
        if (onlyTrueTypes.size() != 0 && onlyTrueTypes != null) {
            confirmMessage.append(" that serves");

            if (onlyTrueTypes.size() == 1) {
                confirmMessage.append(" " + onlyTrueTypes.get(0) + " food.");
            }
            if (onlyTrueTypes.size() == 2) {
                confirmMessage.append(" " + onlyTrueTypes.get(0) + " and " + onlyTrueTypes.get(1) + " food.");
            }
            if (onlyTrueTypes.size() >= 3){
                int i = 0;
                while (i <= onlyTrueTypes.size() - 2) {
                    confirmMessage.append(" ");
                    confirmMessage.append(onlyTrueTypes.get(i));
                    confirmMessage.append(", ");
                    i++;
                }
                confirmMessage.append("and ");
                confirmMessage.append(onlyTrueTypes.get(i));
                confirmMessage.append(" food.");
            }
        }
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
//            System.out.println("Lat = " + location.get("lat"));
//            System.out.println("Lng = " + location.get("lng"));
        } catch (Exception e) {
        } finally {
            model.addAttribute("title", "NumNums! Display");

//            Set parameters for Query
            BigDecimal latitude = aSearch.getLatitude();
            BigDecimal longitude = aSearch.getLongitude();
            int distance = aSearch.getDistance();

            ArrayList<Restaurant> searchResults = RestaurantService.locateRestaurants(latitude, longitude, distance);

                        //filter searchResults
            ArrayList<Restaurant> filteredSearchResults = new ArrayList<>();


            if (onlyTrueTypes != null && onlyTrueTypes.size() != 0) {
                for (int i = 0; i < searchResults.size(); i++){
                    if (onlyTrueTypes.contains("gluten-free") && searchResults.get(i).isGlutenFree()){
                    } else if (onlyTrueTypes.contains("lactose-free") && searchResults.get(i).isLactoseFree()){
                    } else if (onlyTrueTypes.contains("vegan") && searchResults.get(i).isVegan()){
                    } else if (onlyTrueTypes.contains("vegetarian") && searchResults.get(i).isVegetarian()){
                    } else if (onlyTrueTypes.contains("non-vegetarian") && searchResults.get(i).isNonVegetarian()) {
                        filteredSearchResults.add(searchResults.get(i));
                    }
                }
            }


            //ArrayLists needed to display markers and infoWindows
            ArrayList<BigDecimal> latList = new ArrayList<>();
            ArrayList<BigDecimal> lngList = new ArrayList<>();
            ArrayList<String> nameList = new ArrayList<>();
            ArrayList<String> addressList = new ArrayList<>();
            ArrayList<String> webList = new ArrayList<>();

            if (filteredSearchResults.size() != 0 && filteredSearchResults != null) {
                for (Restaurant res : filteredSearchResults) {
                    latList.add(res.getLatitude());
                    lngList.add(res.getLongitude());
                    nameList.add(res.getRestaurantName());
                    webList.add(res.getWebAddress());
                    addressList.add(res.getStreetAddress());
                }

                System.out.println("nameList: " + nameList);
                System.out.println("webList: " + webList);
                System.out.println("addressList: " + addressList);
                System.out.println("latList: " + latList + "lngList: " + lngList);
            }

            //            convert string arrays to json

            ObjectMapper mapper = new ObjectMapper();
            String json = "";
            try {
                json = mapper.writeValueAsString(nameList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapperTwo = new ObjectMapper();
            String jsonTwo = "";
            try {
                jsonTwo = mapperTwo.writeValueAsString(addressList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapperThree = new ObjectMapper();
            String jsonThree = "";
            try {
                jsonThree = mapperThree.writeValueAsString(webList);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(filteredSearchResults);
            model.addAttribute("latList", latList);
            model.addAttribute("lngList", lngList);
            model.addAttribute("nameList", json);
            model.addAttribute("addressList", jsonTwo);
            model.addAttribute("webList", jsonThree);
            model.addAttribute("restaurants", filteredSearchResults);

            model.addAttribute("confirmMessage", confirmMessage);
        }
        return "home/display";
    }
}