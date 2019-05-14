package com.spinningnoodle.communitymanager.stepdefinitions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spinningnoodle.communitymanager.AbstractDefs;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class UpcomingDatesStepDefinitions extends AbstractDefs {
    
    @Given("The client is signed in")
    public void theClientSignsIn() {
        
    }

    @When("^the client calls /upcoming$")
    public void theClientCallsUpcoming() throws Throwable {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        executeGet("/upcoming");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        mvc.perform(get("/upcoming"))
            .andExpect(status().is(statusCode))
            .andExpect(content().contentType("application/hal+json"));
    }

    @And("^the client receives server version (.+)$")
    public void theClientReceivesServerVersion(String version) throws Throwable {
        //assertThat(latestResponse.getBody(), is(version)) ;
    }
}
