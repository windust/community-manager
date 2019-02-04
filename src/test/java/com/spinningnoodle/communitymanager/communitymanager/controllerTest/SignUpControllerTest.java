package com.spinningnoodle.communitymanager.communitymanager.controllerTest;


import com.spinningnoodle.communitymanager.controller.SignUpController;
import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class SignUpControllerTest {
    
    private SignUpController signUpController;
    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
    }
    
    @Test
    public void venueReturnsDatesPageWithToken() throws InvalidUserException {
        Assertions.assertEquals("available_dates", signUpController.venue("something"));
    }
    
    @Test
    public void venueThrowsExceptionWithInvalidToken(){
        Assertions.assertThrows(InvalidUserException.class, () -> signUpController.venue("invalid"));
    }
    
    @Test
    public void venueThrowsExceptionWithNoToken(){
        Assertions.assertThrows(InvalidUserException.class, () -> signUpController.venue(""));
    }
}