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
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    TODO look into localizing variables
    because if fields are shared between users then
    multiple users signing up at the same time will
    break application
    */
    String currentToken;
    String responderName;
    LocalDate requestedDate;
    String hostingMessage = "";
    String alertMessage = "";
    boolean requestedDateAvailable = true;
    boolean alert = false;
    boolean hostingRequestedDate = false;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
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
            Venue venue = model.getVenueByToken(token);
            
            generateSessionVariables(session, venue, "venue");
            
            return "available_dates";
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
    }
    
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
        List<Meetup> meetups = model.getAllMeetups();
        Response response = responder.getResponse();
        currentToken = responder.getToken();
        this.responderName = responder.getName();
        this.requestedDate = responder.getRequestedDate();
    
        this.requestedDateAvailable = isDateAvailable(meetups, requestedDate);
    
        if(!requestedDateAvailable){
            setHostingRequestedDate(meetups);
        }
    
        this.hostingMessage = getHostingMessage(response);
    
        session.setAttribute("meetups", meetups);
        session.setAttribute(responderType, responder);
        session.setAttribute("hostingMessage", this.hostingMessage);
        session.setAttribute("ask", this.requestedDateAvailable && response.equals(Response.UNDECIDED));
        session.setAttribute("alert", alert);
        session.setAttribute("alertMessage", alertMessage);
    }
    
    private String getHostingMessage(Response response){
        if(requestedDateAvailable && response.equals(Response.UNDECIDED)){
            return "Can you host on " + requestedDate.format(dateFormat) + "?";
        }
        else if(response.equals(Response.DECLINED)){
            return "Thank you for your consideration.";
        }
        else if(!requestedDateAvailable && !hostingRequestedDate){
            return "Thank you for volunteering but " + requestedDate.format(dateFormat) + " is already being hosted by another venue.";
        }
        else if(hostingRequestedDate){
            return "Thank you for hosting on " + requestedDate.format(dateFormat) + ", Contact your SeaJUG contact to cancel.";
        }
        else if(!hostingRequestedDate && response.equals(Response.ACCEPTED)){
            //assumes venue cancelled and SeaJUG volunteer removed them
            //from meetup and then changes venue.response to reflect this
            boolean success = model.setVenueForMeetup(responderName, "notHosting", requestedDate);
            if(success){
                return getHostingMessage(Response.DECLINED);
            }
            else{
                throw new IllegalArgumentException("Unable to update response");
            }
        }
        else{
            return "Unable to generate proper response given: "
                + requestedDate.format(dateFormat) + ", " + requestedDateAvailable + ", " + response;
        }
    }
    
    private boolean isDateAvailable(List<Meetup> meetups, LocalDate date) {
        for(Meetup meetup : meetups){
            if(meetup.getDate().equals(date) && meetup.getVenue().equals("")){
                return true;
            }
        }
        
        return false;
    }
    
    private void setHostingRequestedDate(List<Meetup> meetups){
        for(Meetup meetup : meetups){
            if(meetup.getDate().equals(this.requestedDate) && meetup.getVenue().equals(
                responderName)){
                hostingRequestedDate = true;
            }
        }
    }
    
    //TODO see if possible to abstract sign up process
    //TODO implement food boolean to sign up venue as food sponsor if true
    @PostMapping("/venueSignUp")
    public String venueSignUp(@RequestParam(name = "meetup") String meetupDate, @RequestParam(name = "food", required = false) String foodDate){
        boolean success;
        
        success = model.setVenueForMeetup(responderName, meetupDate, requestedDate);
        if(!foodDate.equals("notHosting")){
            model.setVenueFoodForMeetup(responderName, foodDate, requestedDate);
        }

        if(!meetupDate.equals(requestedDate) && !meetupDate.equals("notHosting")){
            alert = true;
            alertMessage = getAlertMessage(success, meetupDate);
        }

        return "redirect:/venue?token=" + this.currentToken;
    }
    
    @PostMapping("/foodSignUp")
    public String foodSignUp(@RequestParam(name = "meetup") String meetupDate){
        boolean success;

        success = model.setFoodForMeetup(responderName, meetupDate, requestedDate);

        if(!meetupDate.equals(requestedDate) && !meetupDate.equals("notHosting")){
            alert = true;
            alertMessage = getAlertMessage(success, meetupDate);
        }
        
        return "redirect:/food?token=" + this.currentToken;
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
