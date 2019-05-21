package com.spinningnoodle.communitymanager.stepdefinitions;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.AbstractDefs;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.UnsupportedEncodingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class UpcomingDatesStepDefinitions extends AbstractDefs {
    @When("^the client calls /upcoming$")
    public void theClientCallsUpcoming() throws Throwable {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        executeGet("/upcoming");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        assertEquals(statusCode, result.getResponse().getStatus());
    }

    @And("^the response is text/html$")
    public void theResponseIsTextHtml() {
        assertEquals("text/html;charset=UTF-8", result.getResponse().getContentType());
    }

    @Then("^the accordion is made of summary tags$")
    public void theAccordionIsMadeOfSummaryTags() throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(result.getResponse().getContentAsString());

        assertTrue(doc.getElementsByTag("summary").size() > 0);
    }

    @And("^the data in the accordion is not default$")
    public void theDataInTheAccordionIsNotDefault() throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(result.getResponse().getContentAsString());

        assertAll("", () -> {
            for(Element fold : doc.getElementsByTag("summary:not(:first-of-type)")) {
                for(Element header : fold.children()) {
                    System.out.println(header.toString());
                    assertNotEquals("Date", header.text());
                    assertNotEquals("Speaker", header.text());
                    assertNotEquals("Venue", header.text());
                }

            }
        });
    }
}
