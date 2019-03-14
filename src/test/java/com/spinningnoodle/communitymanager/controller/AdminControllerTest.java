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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

public class AdminControllerTest {
    
    //objects to test
    private GoogleSheetsManager model;
    private AdminController adminController;
    private HttpSession session;
    
    //data to compare to
    private List<Map<String, String>> expectedMeetups;
    
    @BeforeEach
    public void initializeController(){
        adminController = new AdminController();
        model = mock(GoogleSheetsManager.class);
        adminController.model = model;
        session = new MockHttpSession();
    
        expectedMeetups = createMeetups();
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("loggedIn is false when application starts")
    public void loggedInStartsAsFalse(){
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Login route directs to login screen")
    public void loginReturnsLoginPage() {
        Assertions.assertEquals("login", adminController.login());
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logging in with good credentials redirects to upcoming dates page")
    public void loginAttemptRedirectsToUpcomingWhenSuccessful() {
        Assertions.assertEquals("redirect:/upcoming", adminController.loginAttempt("username", "password"));
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logging in with bad credentials redirects to login page")
    public void loginAttemptRedirectsToLoginWhenFailed(){
        Assertions.assertEquals("redirect:/", adminController.loginAttempt("incorrect", "wrong"));
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("loggedIn changed to true when given correct credentials")
    public void loginAttemptSuccessful(){
        adminController.loginAttempt("username", "password");
        Assertions.assertTrue(adminController.loggedIn);
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("loggedIn remains false if attempted to login with bad credentials")
    public void loginAttemptWithBadCredentials(){
        adminController.loginAttempt("incorrect", "wrong");
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logout button redirects to login page")
    public void logoutRedirectsToLoginPage(){
        Assertions.assertEquals("redirect:/", adminController.logout());
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logout button sets loggedIn to false")
    public void logoutSetsLoggedInToFalse() {
        ReflectionTestUtils.setField(adminController, "loggedIn", true);
        adminController.logout();
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    //TODO refactor once oauth is implemented
    @Test
    @DisplayName("meetup route throws InvalidUserException if user isn't logged in")
    public void meetupRouteThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.meetup("1", session));
    }
    
    //TODO refactor once oauth is implemented
    @Test
    @DisplayName("getToken route throws InvalidUserException is user isn't logged in")
    public void getTokenThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.getToken("1"));
    }
    
    //TODO refactor once oauth is implemented
    @Test
    @DisplayName("upcoming dates route throws InvalidUserException is user isn't logged in")
    public void upcomingThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.upcomingDates(session));
    }
    
    @Test
    @DisplayName("meetup route renders meetup details page when user is logged in")
    public void meetupRouteReturnsMeetupPageIfLoggedIn() throws InvalidUserException {
        adminController.loggedIn = true;
        Assertions.assertEquals("meetup", adminController.meetup("1", session));
    }
    
    @Test
    @DisplayName("meetup route finds specified meetup and sends to view")
    public void meetupGetsSpecificMeetupForView() throws InvalidUserException {
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        Map<String, String> expected = expectedMeetups.get(1);
        
        adminController.loggedIn = true;
        adminController.meetup("2", session);
        
        Assertions.assertEquals(expected, session.getAttribute("meetup"));
    }
    
    @Test
    @DisplayName("upcoming date route renders upcoming dates page")
    public void upcomingReturnsUpcomingPageIfLoggedIn() throws InvalidUserException {
        adminController.loggedIn = true;
        Assertions.assertEquals("upcoming_dates", adminController.upcomingDates(session));
    }
    
    @Test
    @DisplayName("upcoming dates displays all meetups")
    public void upcomingGivesViewMeetupsFromModel() throws InvalidUserException {
        adminController.loggedIn = true;
        Comparator<Map<String, String>> compareMeetups = Comparator.comparing(o -> o.get("primaryKey"));
        
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        
        adminController.upcomingDates(session);
        List<Map<String, String>> actual = (List<Map<String, String>>) session.getAttribute("meetups");
        
        expectedMeetups.sort(compareMeetups);
        actual.sort(compareMeetups);
        
        Assertions.assertEquals(expectedMeetups, actual);
    }
    
    @Test
    @DisplayName("getToken returns a token for specified venue")
    public void getTokenReturnSpecificToken() throws InvalidUserException {
        when(model.requestHost("1", "1/14/2019")).thenReturn("123N");
        String expected = model.requestHost("1", "1/14/2019");
        
        adminController.loggedIn = true;
        
        Assertions.assertEquals(expected, adminController.getToken("venueKey=1&date=1/14/2019"));
    }
    
    private List<Map<String, String>> createMeetups(){
        List<Map<String, String>> meetupData = new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        
        row.put("primaryKey", "1");
        row.put("date","01/14/2019");
        row.put("speaker","Purple");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", "Excellent");
        meetupData.add(row);
        
        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("date","02/19/2019");
        row.put("speaker","Yellow");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", "Amazing");
        meetupData.add(row);
        
        row = new HashMap<>();
        row.put("primaryKey", "3");
        row.put("date","03/22/2019");
        row.put("speaker","John Doe");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", null);
        meetupData.add(row);
        
        return meetupData;
    }
    
    private List<Map<String, String>> createVenues(){
        List<Map<String, String>> venueData = new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        
        row.put("primaryKey", "1");
        row.put("name","Excellent");
        row.put("address","100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","123N");
        row.put("requestedHostingDate", "01/14/2019");
        venueData.add(row);
        
        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("name","Amazing");
        row.put("address","200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","143N");
        row.put("requestedHostingDate", "01/14/2019");
        venueData.add(row);
        
        return venueData;
    }
}