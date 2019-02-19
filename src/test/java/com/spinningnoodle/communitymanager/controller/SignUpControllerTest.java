package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.exceptions.InvalidUserException;
import com.spinningnoodle.communitymanager.model.collections.DummyMeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.DummyVenueCollection;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

public class SignUpControllerTest {
    
    private final String validToken = "Expedia-valid";
    
    private SignUpController signUpController;
    private HttpSession session;
    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
        signUpController.meetupCollection = new DummyMeetupCollection();
        signUpController.venueCollection = new DummyVenueCollection();
        session = new MockHttpSession();
    }
    
    @Test
    public void venueReturnsDatesPageWithToken() throws InvalidUserException {
        Assertions.assertEquals("available_dates", signUpController.venue(validToken, session));
    }
    
    @Test
    public void venueThrowsExceptionWithInvalidToken(){
        Assertions.assertThrows(InvalidUserException.class, () -> signUpController.venue("invalid", session));
    }
    
    @Test
    public void venueThrowsExceptionWithNoToken(){
        Assertions.assertThrows(InvalidUserException.class, () -> signUpController.venue("", session));
    }
    
    @Test
    public void venueSetsHostingMessage() throws InvalidUserException {
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(signUpController.hostingMessage, session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void venueSetsDateAvailable() throws InvalidUserException {
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(signUpController.dateAvailable, session.getAttribute("dateAvailable"));
    }
    
    @Test
    public void venueSetsCurrentToken() throws InvalidUserException {
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals(validToken, signUpController.currentToken);
    }
    
    @Test
    public void messageForWhenVenueHostsAvailableMeetup() throws InvalidUserException {
        signUpController.venue(validToken, session);
        signUpController.venueSignUp("2");
        signUpController.venue(validToken, session);
        
        Assertions.assertEquals("Thank you for hosting on 2/14/2019. \nContact Freddy to cancel.", session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void messageForWhenVenueHostsUnavailableMeetup() throws InvalidUserException {
        signUpController.venue(validToken, session);
        signUpController.venueSignUp("1");
        signUpController.venue(validToken, session);
    
        Assertions.assertEquals("Thank you for volunteering but this date already has a host", session.getAttribute("hostingMessage"));
    }
    
    @Test
    public void messageForWhenVenueDeclinesHostsMeetup() throws InvalidUserException {
        signUpController.venue(validToken, session);
        signUpController.venueSignUp("notHosting");
        signUpController.venue(validToken, session);
    
        Assertions.assertEquals("Thank you for your consideration.", session.getAttribute("hostingMessage"));
    }
}