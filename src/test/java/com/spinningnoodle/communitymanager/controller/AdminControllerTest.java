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
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
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
    private List<FoodSponsor> expectedFood;

    
    @BeforeEach
    public void initializeController(){
        adminController = new AdminController();
        model = mock(GoogleSheetsManager.class);
        adminController.model = model;
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
    
        expectedMeetups = createMeetups();
        expectedVenues = createVenues();
        expectedFood = createFood();
    }
    
    @Test
    @DisplayName("Login route directs to login screen")
    public void loginReturnsLoginPage() {
        Assertions.assertEquals("login", adminController.login());
    }
    
    @Test
    @DisplayName("Logout button redirects to login page")
    public void logoutRedirectsToLoginPage(){
        Assertions.assertEquals("redirect:/", adminController.logOut(request));
    }

    @Test
    @DisplayName("meetup route renders meetup details page when user is logged in")
    public void meetupRouteReturnsMeetupPageIfLoggedIn() {
        when(model.getAllVenues()).thenReturn(expectedVenues);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        
        Assertions.assertEquals("meetup", adminController.meetup("1", session));
    }

    @Test
    @DisplayName("meetup route finds specified meetup and sends to view")
    public void meetupGetsSpecificMeetupForView() {
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        Meetup expected = expectedMeetups.get(1);

        adminController.meetup("2", session);

        Assertions.assertEquals(expected, session.getAttribute("meetup"));
    }

    @Test
    @DisplayName("meetup route gets and displays all venues if meetup doesn't have a venue")
    public void meetupGetsVenues(){
        when(model.getAllVenues()).thenReturn(expectedVenues);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        when(model.getAllFoodSponsors(expectedMeetups.get(0))).thenReturn(expectedFood);

        adminController.meetup("3", session);

        Assertions.assertEquals(expectedVenues, session.getAttribute("venues"));
    }

    @Test
    @DisplayName("upcoming date route renders upcoming dates page")
    public void upcomingReturnsUpcomingPageIfLoggedIn() throws InvalidUserException {
        Assertions.assertEquals("upcoming_dates", adminController.upcomingDates(session));
    }

    @Test
    @DisplayName("upcoming dates displays all meetups")
    public void upcomingGivesViewMeetupsFromModel() throws InvalidUserException {
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
        meetup.setFood("Pizza Hut");
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(2);
        meetup.setDate(LocalDate.of(2019,2,19));
        meetup.setSpeaker("Yellow");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue("Amazing");
        meetup.setFood("");
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(3);
        meetup.setDate(LocalDate.of(2019,3,22));
        meetup.setSpeaker("John Doe");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue("");
        meetup.setFood("");
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
    
    private List<FoodSponsor> createFood(){
        List<FoodSponsor> foodData = new ArrayList<>();
        FoodSponsor food = new FoodSponsor();
        Map<String, String> setToken = new HashMap<>();
        
        setToken.put("token", "123Nf");
        food = food.build(setToken);
        food.setPrimaryKey(1);
        food.setName("Pizza Hot");
        food.setAddress("125 N Seattle");
        food.setContactPerson("Bart");
        food.setContactEmail("simpson@pizahut.com");
        food.setContactPhone("360-123-4567");
        food.setContactAltPhone("");
        food.setRequestedDate(LocalDate.of(2019,1,14));
        food.setResponse(Response.UNDECIDED);
        foodData.add(food);
        
        food = new FoodSponsor();
        setToken = new HashMap<>();
        setToken.put("token", "143N");
        food = food.build(setToken);
        food.setPrimaryKey(2);
        food.setName("Logical");
        food.setAddress("345 Alaska Way");
        food.setContactPerson("Jared");
        food.setContactEmail("jared@logical.com");
        food.setContactPhone("425-123-4568");
        food.setContactAltPhone("");
        food.setRequestedDate(LocalDate.of(2019,1,14));
        food.setResponse(Response.UNDECIDED);
        foodData.add(food);
        
        return foodData;
    }
}