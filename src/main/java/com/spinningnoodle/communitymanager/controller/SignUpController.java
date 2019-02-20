package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
    GoogleSheetsManager model = new GoogleSheetsManager();
    
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
            venueName = meetups.get(0).get("name");
            requestedDate = meetups.get(0).get("requestedDate");
            meetups.remove(0);
            
            if(requestedDate == null){
                dateAvailable = false;
            }
            if(hostingMessage == null){
                hostingMessage = "Can you host the meetup on " + requestedDate;
            }
            
            session.setAttribute("meetups", meetups);
            session.setAttribute("greeting", "Welcome, " + venueName);
            session.setAttribute("hostingMessage", hostingMessage);
            session.setAttribute("requestedDate", requestedDate);
            session.setAttribute("dateAvailable", dateAvailable);
            
            return "available_dates";
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
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
