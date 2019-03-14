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

import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

public class SignUpControllerTest {
    
    private final String validToken = "123N";
    
    //objects to test
    private SignUpController signUpController;
    private GoogleSheetsManager model;
    private HttpSession session;
    
    //data to compare to
    List<Map<String, String>> expectedMeetupsWithVenue;

    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
        model = mock(GoogleSheetsManager.class);
        signUpController.model = model;
        session = new MockHttpSession();
        
        expectedMeetupsWithVenue = createMeetupsAndVenueWithToken("yes");
    }
    
    @Test
    @DisplayName("venue route renders venue page given a valid token")
    public void venueReturnsDatesPageWithToken() {
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        Assertions.assertEquals("available_dates", signUpController.venue(validToken, session));
    }
    
    @Test
    public void venueThrowsExceptionWithInvalidToken(){
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> signUpController.venue("invalid", session));
    }
    
    @Test
    public void venueThrowsExceptionWithNoToken(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> signUpController.venue("", session));
    }
    
    @Test
    public void venueSetsHostingMessageInSession(){
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(signUpController.hostingMessage, session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void venueSetsDateAvailableInSession(){
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(signUpController.requestedDateAvailable, session.getAttribute("requestedDateAvailable"));
    }
    
    @Test
    public void venueSetsCurrentToken(){
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(validToken, signUpController.currentToken);
    }
    
    @Test
    public void messageForWhenVenueHostsAvailableMeetup(){
        String availableDate = "03/22/2019";
        signUpController.venue(validToken, session);
        signUpController.venueSignUp(availableDate);
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for hosting on " + availableDate + ". \nContact Freddy to cancel.", session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void messageForWhenVenueHostsUnavailableMeetup() {
        String unavailableDate = "02/19/2019";
        signUpController.venue(validToken, session);
        signUpController.venueSignUp(unavailableDate);
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for volunteering but this date already has a host", session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void messageForWhenVenueDeclinesHostsMeetup(){
        signUpController.venue(validToken, session);
        signUpController.venueSignUp("notHosting");
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for your consideration.", session.getAttribute("hostingMessage"));
    }
    
    private List<Map<String, String>> createMeetupsAndVenueWithToken(String response){
        List<Map<String, String>> meetupsWithVenue = new ArrayList<>();
        
        meetupsWithVenue.addAll(createMeetups());
        meetupsWithVenue.add(0, createPartialVenue(response));
        
        return meetupsWithVenue;
    }
    
    private Map<String, String> createPartialVenue(String response){
        Map<String, String> partialVenue = new HashMap<>();
    
        partialVenue.put("name","Excellent");
        partialVenue.put("requestedHostingDate", "01/14/2019");
        partialVenue.put("response", response);
        
        return partialVenue;
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
}