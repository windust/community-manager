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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller that sets routes to pages
 * used for non-admins to register for
 * meetups
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@Controller
public class SignUpController {
    
    @Autowired
    DataManager model;
    
    String currentToken;
    String venueName;
    String requestedDate;
    String hostingMessage;
    boolean dateAvailable = true;
    
//    public SignUpController(){
//        model = new GoogleSheetsManager();
//        dateAvailable = true;
//    }
    
    //TODO update javadocs
    /**
     * Route for venues to sign up to host meetups
     * if they have a valid token
     * @param token - token for venue to access page
     * @return available_dates - html page to render
     * @throws InvalidUserException - if user has
     * invalid token
     */
    @GetMapping("/venue")
    public String venue(@RequestParam(name = "token") String token, HttpSession session) {
        try{
            List<Map<String, String>> meetups;
            meetups = model.getMeetupByVenueToken(token);
            currentToken = token;
            this.venueName = meetups.get(0).get("name");
            this.requestedDate = meetups.get(0).get("requestedDate");
            meetups.remove(0);
            
            this.dateAvailable = isDateAvailable(meetups);
            this.hostingMessage = determineHostingMessage();
            
            session.setAttribute("meetups", meetups);
            session.setAttribute("venueName", this.venueName);
            session.setAttribute("hostingMessage", this.hostingMessage);
            session.setAttribute("requestedDate", this.requestedDate);
            session.setAttribute("dateAvailable", this.dateAvailable);
            
            return "available_dates";
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
    }
    
    private String determineHostingMessage(){
        return "Can you host the meetup on " + requestedDate + "?";
    }
    
    public boolean isDateAvailable(List<Map<String, String>> meetups) {
        for(Map<String, String> meetup : meetups){
            if(!meetup.get("venue").isEmpty()){
                return false;
            }
        }
        
        return true;
    }
    
    @PostMapping("/venueSignUp")
    public String venueSignUp(@RequestParam(name = "meetup") String meetupDate){
        String message;
        boolean success;
        
        if(meetupDate.equals("notHosting")){
            message = "Thank you for your consideration.";
        }
        else {
            success = model.setVenueForMeetup(venueName, meetupDate);
            
            if(success){
                message = "Thank you for hosting on " + meetupDate + ". \nContact Freddy to cancel.";
            }
            else{
                message = "Thank you for volunteering but this date already has a host";
                if(meetupDate.equals(requestedDate)){
                    this.dateAvailable = false;
                }
            }
        }
        
        this.hostingMessage = message;
        return "redirect:/venue?token=" + this.currentToken;
    }
    
    //TODO create speakers sign up page
//    @GetMapping("/speaker")
//    public String speaker(){
//        return "login.html";
//    }
}
