package com.spinningnoodle.communitymanager.controller;


import com.spinningnoodle.communitymanager.model.DummyGoogleSheetsManager;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

public class SignUpControllerTest {
    
    private final String validToken = "123N";
    
    private SignUpController signUpController;
    private HttpSession session;
    
    @BeforeEach
    public void initializeController(){
        signUpController = new SignUpController();
        signUpController.model = new DummyGoogleSheetsManager();
        session = new MockHttpSession();
    }
    
    @Test
    public void venueReturnsDatesPageWithToken() {
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
        
        Assertions.assertEquals(signUpController.dateAvailable, session.getAttribute("dateAvailable"));
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
}