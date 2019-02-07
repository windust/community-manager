package com.spinningnoodle.communitymanager.controllertest;

import com.spinningnoodle.communitymanager.controller.AdminController;
import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class AdminControllerTest {
    
    private AdminController adminController;
    
    @BeforeEach
    public void initializeController(){
        adminController = new AdminController();
    }
    
    @Test
    public void loggedInStartsAsFalse(){
        Assertions.assertEquals(ReflectionTestUtils.getField(adminController, "loggedIn"), false);
    }
    
    @Test
    public void loginReturnsLoginPage() {
        Assertions.assertEquals("login", adminController.login());
    }
    
    //TODO change from meetup to dashboard once dashboard page is complete
    @Test
    public void loginAttemptRedirectsToMeetupWhenSuccessful() {
        Assertions.assertEquals("redirect:/meetup", adminController.loginAttempt("username", "password"));
    }
    
    @Test
    public void loginAttemptRedirectsToLoginWhenFailed(){
        Assertions.assertEquals("redirect:/", adminController.loginAttempt("incorrect", "wrong"));
    }
    
    @Test
    public void loginAttemptSuccessful(){
        adminController.loginAttempt("username", "password");
        Assertions.assertEquals(ReflectionTestUtils.getField(adminController, "loggedIn"), true);
    }
    
    @Test
    public void loginAttemptFailed(){
        adminController.loginAttempt("incorrect", "wrong");
        Assertions.assertEquals(ReflectionTestUtils.getField(adminController, "loggedIn"), false);
    }
    
    @Test
    public void logoutRedirectsToLoginPage(){
        Assertions.assertEquals("redirect:/", adminController.logout());
    }
    
    @Test
    public void logoutSetsLoggedInToFalse() {
        ReflectionTestUtils.setField(adminController, "loggedIn", true);
        adminController.logout();
        Assertions.assertEquals(false, ReflectionTestUtils.getField(adminController, "loggedIn"));
    }
    
    //TODO create dashboard tests
//    @Test
//    public void dashboard() {
//    }
    
    @Test
    public void meetupReturnsMeetupPageIfLoggedIn() throws InvalidUserException {
        ReflectionTestUtils.setField(adminController, "loggedIn", true);
        Assertions.assertEquals("meetup", adminController.meetup());
    }
    
    @Test
    public void meetupThrowsExceptionIfNotLoggedIn(){
        ReflectionTestUtils.setField(adminController, "loggedIn", false);
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.meetup());
    }
    
    @Test
    public void getTokenThrowsExceptionIfNotLoggedIn(){
        ReflectionTestUtils.setField(adminController, "loggedIn", false);
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.getToken());
    }
    
    //TODO create token to retrieve from DB
//    @Test
//    public void getTokenReturnsTokenWhenLoggedIn() {
//    }
    
    //TODO create google sheets Page
//    @Test
//    public void googleSheetsPage() {
//    }
}