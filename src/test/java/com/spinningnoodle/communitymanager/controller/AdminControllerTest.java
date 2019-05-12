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
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class AdminControllerTest {
    
    //objects to test
    private GoogleSheetsManager model;
    private AdminController adminController;
    private HttpSession session;
    private HttpServletRequest request;
    private OAuth2AuthenticationToken token;

    //data to compare to
    private List<Meetup> expectedMeetups;
    private List<Venue> expectedVenues;

    private Map<String, Object> properties;
    
    @BeforeEach
    public void initializeController(){
        adminController = new AdminController();
        model = mock(GoogleSheetsManager.class);
        adminController.model = model;
        session = new MockHttpSession();
        request = new MockHttpServletRequest();

//        properties = token.getPrincipal().getAttributes();
    
        expectedMeetups = createMeetups();
        expectedVenues = createVenues();
    }
    
    @Test
    @DisplayName("loggedIn is false when application starts")
    public void loggedInStartsAsFalse(){
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    @DisplayName("Login route directs to login screen")
    public void loginReturnsLoginPage() {
        Assertions.assertEquals("login", adminController.login());
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logging in with good credentials redirects to upcoming dates page")
    @Disabled
    public void loginSuccessRedirectsToUpcomingWhenSuccessful() {
        when(model.verifyAdmin("")).thenReturn(true);
        when(properties.get("email")).thenReturn("kevanbarter@gmail.com");
        Assertions.assertEquals("redirect:/upcoming", adminController.loginSuccess(request, token));
    }
    
    @Test
    @DisplayName("loggedIn changed to true when given correct credentials")
    @Disabled
    public void loginSuccessful(){
        when(model.verifyAdmin("")).thenReturn(true);
        adminController.loginSuccess(request, token);
        Assertions.assertTrue(adminController.loggedIn);
    }
    
    @Test
    @DisplayName("loggedIn remains false if attempted to login with bad credentials")
    @Disabled
    public void loginAttemptWithBadCredentials(){
        when(model.verifyAdmin("")).thenReturn(false);
        adminController.loginSuccess(request, token);
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    @DisplayName("Logout button redirects to login page")
    public void logoutRedirectsToLoginPage(){
        Assertions.assertEquals("redirect:/", adminController.logOut(request));
    }
    
    //TODO replace with oauth tests once oauth is implemented
    @Test
    @DisplayName("Logout button sets loggedIn to false")
    public void logoutSetsLoggedInToFalse() {
        adminController.loggedIn = true;
        adminController.logOut(request);
        Assertions.assertFalse(adminController.loggedIn);
    }
    
    @Test
    @DisplayName("meetup route throws InvalidUserException if user isn't logged in")
    public void meetupRouteThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.meetup("1", session));
    }
    
    @Test
    @DisplayName("getVenueToken route throws InvalidUserException is user isn't logged in")
    public void getTokenThrowsInvalidUserExceptionIfNotLoggedIn(){
        adminController.loggedIn = false;
        Assertions.assertThrows(InvalidUserException.class, () -> adminController.getVenueToken("1"));
    }
    
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
        Meetup expected = expectedMeetups.get(1);
        
        adminController.loggedIn = true;
        adminController.meetup("2", session);
        
        Assertions.assertEquals(expected, session.getAttribute("meetup"));
    }
    
    @Test
    @DisplayName("meetup route gets and displays all venues")
    public void meetupGetsVenues() throws InvalidUserException {
        when(model.getAllVenues()).thenReturn(expectedVenues);
        
        adminController.loggedIn = true;
        adminController.meetup("1", session);
        
        Assertions.assertEquals(expectedVenues, session.getAttribute("venues"));
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
        Comparator<Meetup> compareMeetups = Comparator.comparing(o -> o.getPrimaryKey());
        
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        
        adminController.upcomingDates(session);
        List<Meetup> actual = (List<Meetup>) session.getAttribute("meetups");
        
        expectedMeetups.sort(compareMeetups);
        actual.sort(compareMeetups);
        
        Assertions.assertEquals(expectedMeetups, actual);
    }
    
    @Test
    @DisplayName("getVenueToken returns a token for specified venue")
    public void getTokenReturnSpecificToken() throws InvalidUserException {
        when(model.requestHost("1", LocalDate.of(2019,1,14))).thenReturn("123N");
        String expected = model.requestHost("1", LocalDate.of(2019,1,14));
        
        adminController.loggedIn = true;
        
        Assertions.assertEquals(expected, adminController.getVenueToken("venueKey=1&date=1/14/2019"));
    }
    
    private List<Meetup> createMeetups(){
        List<Meetup> meetupData = new ArrayList<>();
        Meetup meetup = new Meetup();
        
        meetup.setPrimaryKey(1);
        meetup.setDate(LocalDate.of(2019,1,14));
        meetup.setSpeaker("Purple");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue("Excellent");
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(2);
        meetup.setDate(LocalDate.of(2019,2,19));
        meetup.setSpeaker("Yellow");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue("Amazing");
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(3);
        meetup.setDate(LocalDate.of(2019,3,22));
        meetup.setSpeaker("John Doe");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue(null);
        meetupData.add(meetup);
        
        return meetupData;
    }
    
    private List<Venue> createVenues(){
        List<Venue> venueData = new ArrayList<>();
        Venue venue = new Venue();
        Map<String, String> setToken = new HashMap<>();
        
        setToken.put("token", "123N");
        venue = venue.build(setToken);
        venue.setPrimaryKey(1);
        venue.setName("Excellent");
        venue.setAddress("100 Nowhere St");
        venue.setCapacity(100);
        venue.setContactPerson("Freddy");
        venue.setContactEmail("freddy@excellent.com");
        venue.setContactPhone("");
        venue.setContactAltPhone("");
        venue.setRequestedDate(LocalDate.of(2019,1,14));
        venueData.add(venue);
        
        venue = new Venue();
        setToken = new HashMap<>();
        setToken.put("token", "143N");
        venue = venue.build(setToken);
        venue.setPrimaryKey(2);
        venue.setName("Amazing");
        venue.setAddress("200 Nowhere St");
        venue.setCapacity(150);
        venue.setContactPerson("Nimret");
        venue.setContactEmail("nimret@amazing.com");
        venue.setContactPhone("");
        venue.setContactAltPhone("");
        venue.setRequestedDate(LocalDate.of(2019,1,14));
        venueData.add(venue);
        
        return venueData;
    }
}