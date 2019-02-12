package com.spinningnoodle.communitymanager.controllertest;


import com.spinningnoodle.communitymanager.controller.SignUpController;
import com.spinningnoodle.communitymanager.controllertest.fakes.DummyMeetupCollection;
import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class SignUpControllerTest {
    
    private SignUpController signUpController;
    private DummyMeetupCollection meetupCollection;
    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
    }
    
    @Test
    public void venueReturnsDatesPageWithToken() throws InvalidUserException {
        Assertions.assertEquals("available_dates", signUpController.venue("valid"));
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