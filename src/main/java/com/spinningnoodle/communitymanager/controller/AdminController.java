package com.spinningnoodle.communitymanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @GetMapping("/")
    public String login(){
        return "login";
    }
    
    @GetMapping("/meetup")
    public String meetup(){
        return "meetup";
    }
}
