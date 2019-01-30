package com.spinningnoodle.communitymanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignUpController {
    @GetMapping("/venue")
    public String venue(){
        return "available_dates.html";
    }
    
    @GetMapping("/speaker")
    public String speaker(){
        return "";
    }
}
