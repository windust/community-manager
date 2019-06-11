package com.spinningnoodle.communitymanager.stepdefinitions;

import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spinningnoodle.communitymanager.AbstractDefs;
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class AvalibleDatesStepDefinitions extends AbstractDefs {

//    @InjectMocks
//    private GoogleSheetsManager googleSheetsManager = new GoogleSheetsManager();
//
//    @Mock
//    private VenueCollection venueCollection;
//
//    @Mock
//    private MeetupCollection meetupCollection;

    @Autowired
    @Mock
    private DataStorage dataStorage;


    @Given("^the following Venues exist:$")
    public void theFollowingVenuesExist(DataTable table) throws IOException {
        dataStorage = mock(GoogleSheets.class);
        when(dataStorage.readAll("venues")).thenReturn(table.asMaps(String.class, String.class));
//        venueCollection = mock(VenueCollection.class);
//        meetupCollection = mock(MeetupCollection.class);
//
//        when(meetupCollection.fetchFromDataStorage()).thenReturn(meetupCollection);
//        when(venueCollection.fetchFromDataStorage()).thenReturn(venueCollection);
//
//        googleSheetsManager.meetupCollection = meetupCollection;
//        googleSheetsManager.venueCollection = venueCollection;
    }

    @And("^the following Meetups exist:$")
    public void theFollowingMeetupsExist(DataTable table) {
//        try {
//            when(googleSheets.readAll("meetups")).thenReturn(table.asMaps(String.class, String.class));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @When("^a Venue goes to '/venue' with the token of 'disney-(\\d+)'$")
    public void aVenueGoesToVenueWithTheTokenOfDisney(int arg0) throws Exception {
        System.out.println(dataStorage.readAll("venues"));
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        executeGet("/venue?token=disney-123");
    }

    @When("^a User goes tp '/venue' with the token of 'does-not-exist'$")
    public void aUserGoesTpVenueWithTheTokenOfDoesNotExist() {
        
    }

    @And("^the Venue clicks 'yes' in the banner$")
    public void theVenueClicksYesInTheBanner() {
    }
}
