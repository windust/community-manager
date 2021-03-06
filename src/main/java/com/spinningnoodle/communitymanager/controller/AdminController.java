package com.spinningnoodle.communitymanager.controller;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */

import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.DataManager;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that handles login features and
 * sets routes for all pages that require admin
 * access.
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@Controller
public class AdminController {
    
    @Autowired
    DataManager model;
    
    /**
     * Route to basic login screen
     * @return login - name of html page to render
     */
    @GetMapping("/")
    public String login(){
        return "login";
    }

    /**
     * loginSuccess route that checks if user is a valid admin
     * from the database email and if so allows the user access
     * to the website after logging in via google OAuth.
     * @param request HttpServletRequest
     * @param authentication OAuth2AuthenticationToken
     * @return redirect to another page depending on if they are allowed access
     */
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletRequest request, OAuth2AuthenticationToken authentication){
        Map<String, Object> properties = authentication.getPrincipal().getAttributes();
        String email = (String) properties.get("email");
        
        if(model.verifyAdmin(email) && authentication.isAuthenticated()){
            return "redirect:/upcoming";
        }
        else{
            authentication.setAuthenticated(false);
            new SecurityContextLogoutHandler().logout(request, null, null);
            return "redirect:/";
        }
    }

    /**
     * Route to log_out the user from the website and route
     * back to login page.
     * @param request HttpServeletRequest
     * @return redirect to login page
     */
    //Route cannot equal "logout" because that is oauth default
    @RequestMapping("/log_out")
    public String logOut(HttpServletRequest request){
        new SecurityContextLogoutHandler().logout(request, null, null);

        return "redirect:/";
    }

    /**
     * Route to basic admin page - The admin page contains
     *          information needed to administer the site.
     * @return admin - name of html page to render
     */
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }


    /**
     * Route to page for displaying upcoming dates
     * @param session - session to store variables for view to display
     * @return upcoming_dates - name of html page to render
     */
    @GetMapping("/upcoming")
    public String upcomingDates(HttpSession session){
        
        List<Meetup> meetups = model.getAllMeetups();
        
        session.setAttribute("meetups", meetups);
        
        return "upcoming_dates";
    }
    
    /**
     * Route to specific meetup page that
     * throws an exception if not logged in
     * @return meetup - name of html page to render
     * @throws InvalidUserException - if user is not
     * logged in.
     */
    @PostMapping("/meetup")
    public String meetup(@RequestParam(name = "meetupKey") String meetupKey, HttpSession session){
        List<Meetup> meetups = model.getAllMeetups();
        Meetup meetup = null;
        for (Meetup mtup: meetups) {
            if(mtup.getPrimaryKey() == Integer.parseInt(meetupKey)){
                meetup = mtup;
                session.setAttribute("meetup", meetup);
                break;
            }
        }

        if(meetup.getVenue().isEmpty()) {
            List<Venue> venues = model.getAllVenues();
            session.setAttribute("venues", venues);
        }

        if(meetup.getFood().isEmpty()) {
            List<FoodSponsor> foodSponsors = model.getAllFoodSponsors(meetup);
            session.setAttribute("foodsponsors", foodSponsors);
        }

        return "meetup";
    }
    
    /**
     * Route refered to by ajax to get a venue's
     * token from DB and returns it
     * @return token for venue requested
     * @throws InvalidUserException - if user is not
     * logged in.
     */
    @RequestMapping(path = "/getVenueToken", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String getVenueToken(@RequestBody String params) {
            String[] args = params.split("&");
            String venueKey = args[0].split("=")[1];
            String date = args[1].split("=")[1];

            return  model.requestHost(venueKey, Entity.convertDate(date));
    }

    /**
     * Route referred to by ajax to get a food's
     * token from DB and returns it.
     * @param params
     * @return token for food required
     * @throws InvalidUserException
     */
    @RequestMapping(path = "/getFoodToken", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String getFoodToken(@RequestBody String params) {
            String[] args = params.split("&");
            String foodKey = args[0].split("=")[1];
            String date = args[1].split("=")[1];
            
            return  model.requestFood(foodKey, Entity.convertDate(date));
    }

    /**
     * Route to page for displaying upcoming dates
     * @param session - session to store variables for view to display
     * @return upcoming_dates - name of html page to render
     */
    @GetMapping("/venue_sheet")
    public String venueSheet(HttpSession session) {
        String url = model.getDatabaseAccessPage();
        session.setAttribute("dbaccess", url);

        return "venue_sheet";
    }
}
