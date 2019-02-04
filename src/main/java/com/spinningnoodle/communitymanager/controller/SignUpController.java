package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    /**
     * Route for venues to sign up to host meetups
     * if they have a valid token
     * @param token - token for venue to access page
     * @return available_dates - html page to render
     * @throws InvalidUserException - if user has
     * invalid token
     */
    @GetMapping("/venue")
    public String venue(@RequestParam(name = "token") String token) throws InvalidUserException {
        if(validToken(token)){
            return "available_dates";
        }
        else{
            throw new InvalidUserException();
        }
        
    }
    
    //TODO create speakers sign up page
//    @GetMapping("/speaker")
//    public String speaker(){
//        return "login.html";
//    }
    
    private boolean validToken(String token){
        return token.equals("something");
    }
}
