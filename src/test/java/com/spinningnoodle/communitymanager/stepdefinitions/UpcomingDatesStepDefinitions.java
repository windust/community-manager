package com.spinningnoodle.communitymanager.stepdefinitions;


import com.spinningnoodle.communitymanager.AbstractDefs;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class UpcomingDatesStepDefinitions extends AbstractDefs {

    @Before
    public void setup() throws Exception {

    }

    @Test
    @When("^the client calls /upcoming$")
    public void theClientCallsUpcoming() throws Throwable {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        executeGet("/upcoming");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        System.out.println(result.getResponse().getStatus());
        // assertThat(result.getResponse().getStatus()).isEqualTo(statusCode);
    }

    @And("^the client receives server version (.+)$")
    public void theClientReceivesServerVersion(String version) throws Throwable {
        //assertThat(latestResponse.getBody(), is(version)) ;
    }
}
