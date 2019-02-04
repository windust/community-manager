package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignUpController {
    @GetMapping("/venue")
    public String venue(@RequestParam(name = "token") String token) throws InvalidUserException {
        if(validToken(token)){
            return "available_dates.html";
        }
        else{
            throw new InvalidUserException();
        }
        
    }
    
    //TODO create speakers sign up page
    @GetMapping("/speaker")
    public String speaker(){
        return "login.html";
    }
    
    private boolean validToken(String token){
        return token.equals("something");
    }
}
