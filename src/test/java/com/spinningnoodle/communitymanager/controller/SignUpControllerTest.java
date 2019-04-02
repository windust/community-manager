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
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.util.ArrayList;
import java.util.List;
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
    private List<Meetup> expectedMeetups;
    private Venue expectedVenue;


    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
        model = mock(GoogleSheetsManager.class);
        signUpController.model = model;
        session = new MockHttpSession();
    
        expectedMeetups = createMeetups("Excellent");
        expectedVenue = createVenue(Response.ACCEPTED);
    }

    @Test
    @DisplayName("venue route renders venue page given a valid token")
    public void venueReturnsDatesPageWithToken() {
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        Assertions.assertEquals("available_dates", signUpController.venue(validToken, session));
    }

    @Test
    @DisplayName("venue route throws IllegalArgumentException if given an invalid token")
    public void venueThrowsExceptionWithInvalidToken(){
        when(model.getVenueByToken("invalid")).thenThrow(new IllegalArgumentException("Invalid Token"));
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> signUpController.venue("invalid", session));
    }

    @Test
    @DisplayName("venue route sets hosting message")
    public void venueSetsHostingMessageInSession(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertEquals(signUpController.hostingMessage, session.getAttribute("hostingMessage"));
    }

    @Test
    @DisplayName("venue route sets meetups as an attribute in the session")
    public void venueSetsSessionMeetupsAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("meetups"));
    }

    @Test
    @DisplayName("venue route sets hosting message as an attribute in the session")
    public void venueSetsSessionHostingMessageAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("hostingMessage"));
    }

    @Test
    @DisplayName("venue route sets venue as an attribute in the session")
    public void venueSetsSessionRequestedDateAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("venue"));
    }

    @Test
    @DisplayName("venue route sets ask as an attribute in the session")
    public void venueSetsSessionAskAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("ask"));
    }

    @Test
    @DisplayName("venue route sets alert as an attribute in the session")
    public void venueSetsSessionAlertAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("alert"));
    }

    @Test
    @DisplayName("venue route sets alert message as an attribute in the session")
    public void venueSetsSessionAlertMessageAttribute(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        signUpController.venue(validToken, session);

        Assertions.assertNotNull(session.getAttribute("alertMessage"));
    }

    @Test
    @DisplayName("Hosting Message for when requested date is available and venue hasn't responded")
    public void hostingMessageWithDateAvailableAndNoResponse(){
        when(model.getVenueByToken(validToken)).thenReturn(createVenue(Response.UNDECIDED));
        when(model.getAllMeetups()).thenReturn(createMeetups(""));
        signUpController.venue(validToken, session);

        Assertions.assertEquals("Can you host on 01/14/2019?", signUpController.hostingMessage);
    }

    @Test
    @DisplayName("Hosting Message for when requested date is available and venue declines")
    public void hostingMessageWithDateAvailableAndResponseIsNo(){
        when(model.getVenueByToken(validToken)).thenReturn(createVenue(Response.DECLINED));
        when(model.getAllMeetups()).thenReturn(createMeetups(""));
        signUpController.venue(validToken, session);

        Assertions.assertEquals("Thank you for your consideration.", signUpController.hostingMessage);
    }

    @Test
    @DisplayName("Hosting Message for when requested date is unavailable")
    public void hostingMessageWithDateUnavailable(){
        when(model.getVenueByToken(validToken)).thenReturn(createVenue(Response.UNDECIDED));
        when(model.getAllMeetups()).thenReturn(createMeetups("Amazing"));
        signUpController.venue(validToken, session);

        Assertions.assertEquals("Thank you for volunteering but 01/14/2019 is already being hosted by another venue.", signUpController.hostingMessage);
    }

    @Test
    @DisplayName("Hosting Message for when requested date is hosted by requested venue")
    public void hostingMessageWithDateHostedByVenue(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(expectedMeetups);
        signUpController.venue(validToken, session);

        Assertions.assertEquals("Thank you for hosting on 01/14/2019, Contact your SeaJUG contact to cancel.", signUpController.hostingMessage);
    }

    @Test
    @DisplayName("Hosting Message for when venue response is yes but venue isn't actually hosting")
    public void hostingMessageWhenVenueSaysYesButIsNotHosting(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(createMeetups(""));
        when(model.setVenueForMeetup("Excellent", "notHosting", "01/14/2019")).thenReturn(true);
        signUpController.venue(validToken, session);

        Assertions.assertEquals("Thank you for your consideration.", signUpController.hostingMessage);
    }

    @Test
    @DisplayName("Hosting Message for when venue response is yes but venue isn't actually hosting throws IllegalArgumentException if unable to update database")
    public void hostingMessageWhenVenueSaysYesButIsNotHostingThrowsException(){
        when(model.getVenueByToken(validToken)).thenReturn(expectedVenue);
        when(model.getAllMeetups()).thenReturn(createMeetups(""));
        when(model.setVenueForMeetup("Excellent", "notHosting", "01/14/2019")).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> signUpController.venue(validToken, session));
    }

    private Venue createVenue(Response response){
        Venue partialVenue = new Venue();

        partialVenue.setName("Excellent");
        partialVenue.setRequestedHostingDate("01/14/2019");
        partialVenue.setResponse(response);

        return partialVenue;
    }
    
    private List<Meetup> createMeetups(String venue){
        List<Meetup> meetupData = new ArrayList<>();
        Meetup meetup = new Meetup();
        
        meetup.setPrimaryKey(1);
        meetup.setDate("01/14/2019");
        meetup.setSpeaker("Purple");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue(venue);
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(2);
        meetup.setDate("02/19/2019");
        meetup.setSpeaker("Yellow");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue("Amazing");
        meetupData.add(meetup);
        
        meetup = new Meetup();
        meetup.setPrimaryKey(3);
        meetup.setDate("03/22/2019");
        meetup.setSpeaker("John Doe");
        meetup.setTopic("How to do Stuff");
        meetup.setDescription("nailing stuff");
        meetup.setVenue(null);
        meetupData.add(meetup);
        
        return meetupData;
    }
}