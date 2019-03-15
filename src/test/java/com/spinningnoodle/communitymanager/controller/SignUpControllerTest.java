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
    private List<Map<String, String>> expectedMeetupsWithVenue;

    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
        model = mock(GoogleSheetsManager.class);
        signUpController.model = model;
        session = new MockHttpSession();
        
        expectedMeetupsWithVenue = createMeetupsAndVenueWithToken("yes", "Excellent");
    }
    
    @Test
    @DisplayName("venue route renders venue page given a valid token")
    public void venueReturnsDatesPageWithToken() {
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        Assertions.assertEquals("available_dates", signUpController.venue(validToken, session));
    }
    
    @Test
    @DisplayName("venue route throws IllegalArgumentException if given an invalid token")
    public void venueThrowsExceptionWithInvalidToken(){
        when(model.getMeetupsByVenueToken("invalid")).thenThrow(new IllegalArgumentException("Invalid Token"));
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> signUpController.venue("invalid", session));
    }
    
    @Test
    @DisplayName("venue route sets hosting message")
    public void venueSetsHostingMessageInSession(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(signUpController.hostingMessage, session.getAttribute("hostingMessage"));
    }
    
    @Test
    @DisplayName("venue route sets meetups as an attribute in the session")
    public void venueSetsSessionMeetupsAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("meetups"));
    }
    
    @Test
    @DisplayName("venue route sets venue name as an attribute in the session")
    public void venueSetsSessionVenueNameAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("venueName"));
    }
    
    @Test
    @DisplayName("venue route sets hosting message as an attribute in the session")
    public void venueSetsSessionHostingMessageAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("hostingMessage"));
    }
    
    @Test
    @DisplayName("venue route sets requested date as an attribute in the session")
    public void venueSetsSessionRequestedDateAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("requestedDate"));
    }
    
    @Test
    @DisplayName("venue route sets ask as an attribute in the session")
    public void venueSetsSessionAskAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("ask"));
    }
    
    @Test
    @DisplayName("venue route sets alert as an attribute in the session")
    public void venueSetsSessionAlertAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("alert"));
    }
    
    @Test
    @DisplayName("venue route sets alert message as an attribute in the session")
    public void venueSetsSessionAlertMessageAttribute(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(expectedMeetupsWithVenue);
        signUpController.venue(validToken, session);
        
        Assertions.assertNotNull(session.getAttribute("alertMessage"));
    }
    
    @Test
    @DisplayName("Hosting Message for when requested date is available and venue hasn't responded")
    public void hostingMessageWithDateAvailableAndNoResponse(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("", ""));
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Can you host on 01/14/2019?", signUpController.hostingMessage);
    }
    
    @Test
    @DisplayName("Hosting Message for when requested date is available and venue declines")
    public void hostingMessageWithDateAvailableAndResponseIsNo(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("no", ""));
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for your consideration.", signUpController.hostingMessage);
    }
    
    @Test
    @DisplayName("Hosting Message for when requested date is unavailable")
    public void hostingMessageWithDateUnavailable(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("", "Amazing"));
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for volunteering but 01/14/2019 is already being hosted by another venue.", signUpController.hostingMessage);
    }
    
    @Test
    @DisplayName("Hosting Message for when requested date is hosted by requested venue")
    public void hostingMessageWithDateHostedByVenue(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("yes", "Excellent"));
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for hosting on 01/14/2019, Contact your SeaJUG contact to cancel.", signUpController.hostingMessage);
    }
    
    @Test
    @DisplayName("Hosting Message for when venue response is yes but venue isn't actually hosting")
    public void hostingMessageWhenVenueSaysYesButIsNotHosting(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("yes", ""));
        when(model.setVenueForMeetup("Excellent", "notHosting", "01/14/2019")).thenReturn(true);
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for your consideration.", signUpController.hostingMessage);
    }
    
    @Test
    @DisplayName("When venue response is yes but venue isn't actually hosting change response to no")
    public void WhenVenueSaysYesButIsNotHostingChangeResponse(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("yes", ""));
        when(model.setVenueForMeetup("Excellent", "notHosting", "01/14/2019")).thenReturn(true);
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("no", signUpController.response);
    }
    
    @Test
    @DisplayName("Hosting Message for when venue response is yes but venue isn't actually hosting throws IllegalArgumentException if unable to update database")
    public void hostingMessageWhenVenueSaysYesButIsNotHostingThrowsException(){
        when(model.getMeetupsByVenueToken(validToken)).thenReturn(createMeetupsAndVenueWithToken("yes", ""));
        when(model.setVenueForMeetup("Excellent", "notHosting", "01/14/2019")).thenReturn(false);
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> signUpController.venue(validToken, session));
    }
    
    private List<Map<String, String>> createMeetupsAndVenueWithToken(String response, String venue){
        List<Map<String, String>> meetupsWithVenue = new ArrayList<>();
        
        meetupsWithVenue.addAll(createMeetups(venue));
        meetupsWithVenue.add(0, createPartialVenue(response));
        
        return meetupsWithVenue;
    }
    
    private Map<String, String> createPartialVenue(String response){
        Map<String, String> partialVenue = new HashMap<>();
    
        partialVenue.put("name","Excellent");
        partialVenue.put("requestedDate", "01/14/2019");
        partialVenue.put("response", response);
        
        return partialVenue;
    }
    
    private List<Map<String, String>> createMeetups(String venue){
        List<Map<String, String>> meetupData = new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        
        row.put("primaryKey", "1");
        row.put("date","01/14/2019");
        row.put("speaker","Purple");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", venue);
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