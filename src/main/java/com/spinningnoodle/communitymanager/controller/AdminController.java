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
    boolean loggedIn = false;
    
    @Autowired
    DataManager model;
    
    /**
     * Route to basic login screen
     * @return login - name of html page to render
     */
    //TODO convert login to auth
    @GetMapping("/")
    public String login(){
        return "login";
    }
    
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletRequest request, OAuth2AuthenticationToken authentication){
        Map<String, Object> properties = authentication.getPrincipal().getAttributes();
        String email = (String) properties.get("email");
        
        if(model.verifyAdmin(email) && authentication.isAuthenticated()){
            loggedIn = true;
            return "redirect:/upcoming";
        }
        else{
            new SecurityContextLogoutHandler().logout(request, null, null);
            return "redirect:/";
        }
    }
    
    //Route cannot equal "logout" because that is oauth default
    @RequestMapping("/log_out")
    public String logOut(HttpServletRequest request){
        new SecurityContextLogoutHandler().logout(request, null, null);

        loggedIn = false;
        return "redirect:/";
    }
    
    /**
     * Route to page for displaying upcoming dates
     * @param session - session to store variables for view to display
     * @return upcoming_dates - name of html page to render
     */
    @GetMapping("/upcoming")
    public String upcomingDates(HttpSession session) throws InvalidUserException {
        if(!loggedIn) {
            throw new InvalidUserException();
        }
        
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
    public String meetup(@RequestParam(name = "meetupKey") String meetupKey, HttpSession session)
        throws InvalidUserException {
        if(!loggedIn) {
            throw new InvalidUserException();
        }

        //TODO consider having this done in the model somewhere
        List<Meetup> meetups = model.getAllMeetups();
        for (Meetup meetup: meetups) {
            if(meetup.getPrimaryKey() == Integer.parseInt(meetupKey)){
                session.setAttribute("meetup", meetup);
            }
        }

        List<Venue> venues = model.getAllVenues();
        session.setAttribute("venues", venues);
        
        return "meetup";
    }
    
    /**
     * Route refered to by ajax to get a venue's
     * token from DB and returns it
     * @return token for venue requested
     * @throws InvalidUserException - if user is not
     * logged in.
     */
    //TODO return token from DB when logged in
    @RequestMapping(path = "/getToken", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String getToken(@RequestBody String params) throws InvalidUserException {
        if(!loggedIn){
            throw new InvalidUserException();
        }
        else{
            String[] args = params.split("&");
            String venueKey = args[0].split("=")[1];
            String date = args[1].split("=")[1];

            return  model.requestHost(venueKey, Entity.convertDate(date));
        }
    }

    /**
     * Route to page for displaying upcoming dates
     * @param session - session to store variables for view to display
     * @return upcoming_dates - name of html page to render
     */
    @GetMapping("/venue_sheet")
    public String venueSheet(HttpSession session) throws InvalidUserException {
        if(!loggedIn) {
            throw new InvalidUserException();
        }

        String url = model.getDatabaseAccessPage();
        session.setAttribute("dbaccess", url);

        return "venue_sheet";
    }
}
