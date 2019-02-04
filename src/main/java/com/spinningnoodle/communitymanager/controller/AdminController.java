package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private boolean loggedIn = false;
    
    //TODO convert login to auth
    @GetMapping("/")
    public String login(){
        return "login";
    }
    
    @PostMapping("/loginAttempt")
    public String loginAttempt(@RequestParam(name = "username", required = false, defaultValue = "username") String username,
                               @RequestParam(name = "password", required = false, defaultValue = "password") String password){
        if(username.equals("username") && password.equals("password")) {
            loggedIn = true;
            return "redirect:/meetup";
        }
        else{
            return "redirect:/";
        }
    }
    
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
    
    @GetMapping("/meetup")
    public String meetup() throws InvalidUserException {
        if(!loggedIn) {
            throw new InvalidUserException();
        }
        return "meetup";
    }
    
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
