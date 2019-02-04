package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller that handles login features and
 * sets routes for all pages that require admin
 * access.
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@Controller
public class AdminController {
    private boolean loggedIn = false;
    
    /**
     * Route to basic login screen
     * @return login - name of html page to render
     */
    //TODO convert login to auth
    @GetMapping("/")
    public String login(){
        return "login";
    }
    
    /**
     * Route to validate given username and password
     * and sets loggedIn to true if credentials are
     * valid
     * @param username - admin username
     * @param password - admin password
     * @return login page if credentials are invalid
     * or dashboard if credentials are valid
     */
    @PostMapping("/loginAttempt")
    public String loginAttempt(@RequestParam(name = "username", required = false, defaultValue = "username") String username,
                               @RequestParam(name = "password", required = false, defaultValue = "password") String password){
        if(username.equals("username") && password.equals("password")) {
            loggedIn = true;
            //TODO change to dashboard(once dashboard is created)
            return "redirect:/meetup";
        }
        else{
            return "redirect:/";
        }
    }
    
    /**
     * Route that sets loggedIn to false and
     * redirects user to login screen
     * @return redirect:/ - redirects user to login page
     */
    @GetMapping("/logout")
    public String logout(){
        loggedIn = false;
        return "redirect:/";
    }
    
    //TODO create dashboard page
//    @GetMapping("/dashboard")
//    public String dashboard(){
//        return "login.html";
//    }
    
    /**
     * Route to specific meetup page that
     * throws an exception if not logged in
     * @return meetup - name of html page to render
     * @throws InvalidUserException - if user is not
     * logged in.
     */
    @GetMapping("/meetup")
    public String meetup() throws InvalidUserException {
        if(!loggedIn) {
            throw new InvalidUserException();
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
    //TODO return token from DB when logged in
    @PostMapping("/getToken")
    public String getToken() throws InvalidUserException {
        if(!loggedIn){
            throw new InvalidUserException();
        }
        else{
            return "meetup";
        }
    }
    
    //TODO create google sheets page
//    @GetMapping("/googleSheets")
//    public String googleSheets(@RequestParam(name = "sheet", required = false, defaultValue = "all") String sheet){
//        return "login.html";
//    }
}
