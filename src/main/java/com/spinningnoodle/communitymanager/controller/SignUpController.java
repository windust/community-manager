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
import com.spinningnoodle.communitymanager.model.collections.ResponderCollection;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.List;
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
    
    /*
    because if fields are shared between users then
    multiple users signing up at the same time will
    break application
    */
    String responderName;
    LocalDate requestedDate;
    String alertMessage = "";
    boolean alert = false;

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
            Venue venue = model.getVenueByToken(token);
            
            generateSessionVariables(session, venue, "venue");
            
            return "available_dates";
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
    }

    /**
     * Route to the food sponsor sign up page
     * @param token String
     * @param session HttpSession
     * @return food sign up page
     */
    @GetMapping("/food")
    public String food(@RequestParam(name = "token") String token, HttpSession session) {
        try{
            FoodSponsor foodSponsor = model.getFoodByToken(token);
            
            generateSessionVariables(session, foodSponsor, "foodSponsor");
            
            return "food_dates";
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
    }
    
    private void generateSessionVariables(HttpSession session, ResponderEntity responder, String responderType){
        List<Meetup> meetups = model.getAllHostedMeetups();
        if(responder instanceof Venue) meetups = model.getAllMeetups();
        Meetup currentMeetup = new Meetup();
        this.responderName = responder.getName();
        this.requestedDate = responder.getRequestedDate();

        for (Meetup meetup : meetups){
            if(meetup.getDate().equals(responder.getRequestedDate())){
                currentMeetup = meetup;
            }
        }
        
        session.setAttribute("meetups", meetups);
        session.setAttribute(responderType, responder);
        session.setAttribute("hostingMessage", model.getMessage(responder));
        session.setAttribute("ask",
            ResponderCollection.isRequestedDateAvailable(currentMeetup, responder)
                && responder.getResponse().equals(Response.UNDECIDED));
        session.setAttribute("alert", alert);
        session.setAttribute("alertMessage", alertMessage);
    }

    /**
     * Venue sign up page post mapping route.
     * @param meetupDate String
     * @param foodDate String
     * @param token String
     * @return venue sign up page
     */
    @PostMapping("/venueSignUp")
    public String venueSignUp(@RequestParam(name = "meetup") String meetupDate,
        @RequestParam(name = "venueKey", required = false, defaultValue = "-1") int venueKey,
        @RequestParam(name = "food", required = false, defaultValue = "empty") String foodDate,
        @RequestParam(name = "token") String token){
        System.out.println(venueKey);
        boolean success;
        success = model.setVenueForMeetup(responderName, meetupDate, requestedDate);
        if(!meetupDate.equals("notHosting") && foodDate.equals("true")){
            model.setVenueFoodForMeetup(responderName, meetupDate, requestedDate);
        }
        if(!meetupDate.equals("notHosting") && foodDate.equals("false")){
            model.setVenueFoodForMeetup(responderName, "notHosting", requestedDate);
        }
        if(!meetupDate.equals(requestedDate) && !meetupDate.equals("notHosting")){
            alert = true;
            alertMessage = getAlertMessage(success, meetupDate);
        }

        return "redirect:/venue?token=" + token;
    }

    /**
     * Food sign up page
     * @param meetupDate String
     * @param token String
     * @return food sign up page.
     */
    @PostMapping("/foodSignUp")
    public String foodSignUp(@RequestParam(name = "meetup") String meetupDate, @RequestParam(name = "token") String token){
        boolean success;

        success = model.setFoodForMeetup(responderName, meetupDate, requestedDate);

        if(!meetupDate.equals(requestedDate) && !meetupDate.equals("notHosting")){
            alert = true;
            alertMessage = getAlertMessage(success, meetupDate);
        }

        return "redirect:/food?token=" + token;
    }

    private String getAlertMessage(boolean successful, String date){
        if(successful){
            return "Thank you for hosting on " + date + ", Contact your SeaJUG contact to cancel.";
        }
        else{
            return "Thank you for volunteering but " + date + " is already being hosted by another venue.";
        }
    }
}
