package com.spinningnoodle.communitymanager.controller;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */

import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.DummyGoogleSheetsManager;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;


public class AdminControllerTest {
    
    private AdminController adminController;
    private HttpSession session;
    
    @BeforeEach
    public void initializeController(){
        adminController = new AdminController();
        adminController.model = new DummyGoogleSheetsManager();
        session = new MockHttpSession();
    }
    
    @Test
    public void loggedInStartsAsFalse(){
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    public void loginReturnsLoginPage() {
        Assertions.assertEquals("login", adminController.login());
    }
    
    //TODO change from meetup to dashboard once dashboard page is complete
    @Test
    public void loginAttemptRedirectsToUpcomingWhenSuccessful() {
        Assertions.assertEquals("redirect:/upcoming", adminController.loginAttempt("username", "password"));
    }
    
    @Test
    public void loginAttemptRedirectsToLoginWhenFailed(){
        Assertions.assertEquals("redirect:/", adminController.loginAttempt("incorrect", "wrong"));
    }
    
    @Test
    public void loginAttemptSuccessful(){
        adminController.loginAttempt("username", "password");
        Assertions.assertTrue(adminController.loggedIn);
    }
    
    @Test
    public void loginAttemptWithBadCredentials(){
        adminController.loginAttempt("incorrect", "wrong");
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    public void logoutRedirectsToLoginPage(){
        Assertions.assertEquals("redirect:/", adminController.logout());
    }
    
    @Test
    public void logoutSetsLoggedInToFalse() {
        ReflectionTestUtils.setField(adminController, "loggedIn", true);
        adminController.logout();
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    public void meetupRouteReturnsMeetupPageIfLoggedIn() throws InvalidUserException {
        adminController.loggedIn = true;
        Assertions.assertEquals("meetup", adminController.meetup("1"));
    }
    
    @Test
    public void meetupRouteThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.meetup("1"));
    }
    
    @Test
    public void getTokenThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.getToken());
    }
    
    @Test
    public void upcomingReturnsUpcomingPageIfLoggedIn() throws InvalidUserException {
        adminController.loggedIn = true;
        Assertions.assertEquals("upcoming_dates", adminController.upcomingDates(session));
    }
    
    @Test
    public void upcomingThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.upcomingDates(session));
    }
    
    @Test
//    @Disabled("necessary model method not yet implemented, code commented out due to compiler error")
    public void upcomingGivesViewMeetupsFromModel() throws InvalidUserException {
        adminController.loggedIn = true;
        List<Map<String, String>> meeutups = adminController.model.getAllMeetups();
        meeutups.sort(
            new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                        return o1.get("primaryKey").compareTo(o2.get("primaryKey"));
                }
            });
        adminController.upcomingDates(session);
        Assertions.assertEquals(meeutups, session.getAttribute("meetups"));
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