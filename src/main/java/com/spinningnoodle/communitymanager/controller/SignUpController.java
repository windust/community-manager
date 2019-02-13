package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
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
    MeetupCollection meetupCollection;
    VenueCollection venueCollection;
    
    String currentToken;
    String hostingMessage;
    boolean dateAvailable;
    
    public SignUpController(){
        this.meetupCollection = new MeetupCollection();
        this.venueCollection = new VenueCollection();
        this.dateAvailable = true;
        this.hostingMessage = "Can you host 2/14?";
    }
    
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
    public String venue(@RequestParam(name = "token") String token, HttpSession session) throws InvalidUserException {
        if(validToken(token)){
            this.currentToken = token;
            
            session.setAttribute("hostingMessage", this.hostingMessage);
            session.setAttribute("dateAvailable", this.dateAvailable);
            return "available_dates";
        }
        else{
            throw new InvalidUserException();
        }
        
    }
    
    @PostMapping("/venueSignUp")
    public String venueSignUp(@RequestParam(name = "meetup") String meetup){
        String message = "";
        
        if(meetup.equals("notHosting")){
            message = "Thank you for your consideration.";
        }
        else {
            try {
                Meetup meetupToUpdate = meetupCollection.getById(Integer.parseInt(meetup));
                
                if(meetupToUpdate.getVenue() != null){
                    message = "Thank you for volunteering but this date already has a host";
                    this.dateAvailable = false;
                }
                else{
                    Venue venueHosting = getVenueByName(getVenueFromToken(currentToken));
                    meetupToUpdate.setVenue(venueHosting);
                    message = "Thank you for hosting on " + meetupToUpdate.getDate() + ". \nContact Freddy to cancel.";
                }
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        this.hostingMessage = message;
        return "redirect:/venue?token=" + this.currentToken;
    }
    
    private String getVenueFromToken(String token){
        String[] pieces = token.split("-");
        String venueName = pieces[0];
        return venueName;
    }
    
    private Venue getVenueByName(String name) throws EntityNotFoundException {
        for(Venue venue : venueCollection.getAll()){
            if (venue.getName().equals(name)){
                return venue;
            }
        }
        
        throw new EntityNotFoundException();
    }
    
    //TODO create speakers sign up page
//    @GetMapping("/speaker")
//    public String speaker(){
//        return "login.html";
//    }
    
    private boolean validToken(String token){
        return token.equals("Expedia-valid");
    }
}
