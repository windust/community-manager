package com.spinningnoodle.communitymanager.stepdefinitions;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.spinningnoodle.communitymanager.AbstractDefs;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class UpcomingDatesStepDefinitions extends AbstractDefs {

    @Test
    @When("^the client calls /upcoming$")
    public void theClientCallsUpcoming() throws Throwable {
        executeGet("http://localhost:" + port + "/upcoming");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : "+ latestResponse.getBody(), currentStatusCode.value(), is(statusCode) );
    }

    @And("^the client receives server version (.+)$")
    public void theClientReceivesServerVersion(String version) throws Throwable {
        assertThat(latestResponse.getBody(), is(version)) ;
    }
}
