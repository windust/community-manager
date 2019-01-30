package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.NotLoggedInException;
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
    public String loginAttempt(@RequestParam(name = "username", required = false, defaultValue = "j") String username,
                               @RequestParam(name = "password", required = false, defaultValue = "p") String password){
        loggedIn = true;
        return "redirect:/meetup";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(){
        return "";
    }
    
    @GetMapping("/meetup")
    public String meetup() throws NotLoggedInException{
        if(!loggedIn) {
            throw new NotLoggedInException();
        }
        return "meetup";
    }
    
    @GetMapping("/googleSheets")
    public String googleSheets(@RequestParam(name = "sheet", required = false, defaultValue = "all") String sheet){
        return "";
    }
}
