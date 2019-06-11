//package com.spinningnoodle.communitymanager.stepdefinitions;
//
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.spinningnoodle.communitymanager.AbstractDefs;
//import cucumber.api.java.en.And;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//
//public class UpcomingDatesStepDefinitions extends AbstractDefs {
//    @When("^the client calls /upcoming$")
//    public void theClientCallsUpcoming() throws Throwable {
//        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        executeGet("/upcoming");
//    }
//
//    @Then("^the client receives status code of (\\d+)$")
//    public void theClientReceivesStatusCodeOf(int statusCode) {
//        assertEquals(statusCode, result.getResponse().getStatus());
//    }
//
//    @And("^the response is text/html$")
//    public void theResponseIsTextHtml() {
//        assertEquals("text/html;charset=UTF-8", result.getResponse().getContentType());
//    }
//
//    @Then("^the accordion is made of summary tags$")
//    public void theAccordionIsMadeOfSummaryTags() {
//        assertTrue(document.getElementsByTag("summary").size() > 0);
//    }
//
//    @And("^the data in the accordion is not default$")
//    public void theDataInTheAccordionIsNotDefault() {
//
//        assertAll("", () -> {
//            for(Element fold : document.getElementsByTag("summary:not(:first-of-type)")) {
//                for(Element header : fold.children()) {
//                    assertNotEquals("Date", header.text());
//                    assertNotEquals("Speaker", header.text());
//                    assertNotEquals("Venue", header.text());
//                }
//
//            }
//        });
//    }
//
//    @And("^the accordion headers are \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
//    public void theAccordionHeadersAre(String arg0, String arg1, String arg2) {
//        Element header = document.getElementsByTag("summary").first();
//
//        for(int i = 0; i < header.children().size(); i++) {
//            Element e = header.child(i);
//            switch (i) {
//                case 0:
//                    assertEquals(arg0, e.text());
//                    break;
//                case 1:
//                    assertEquals(arg1, e.text());
//                    break;
//                case 2:
//                    assertEquals(arg2, e.text());
//                    break;
//                default:
//                    assertEquals(3, header.children().size());
//                    break;
//            }
//        }
//    }
//
//    @Then("^the navigation bar appears on the page$")
//    public void theNavigationBarAppearsOnThePage() {
//        assertNotNull(document.getElementsByTag("nav"));
//    }
//
//    @And("^the navigation bar has a link to \"([^\"]*)\" with a label of \"([^\"]*)\"$")
//    public void theNavigationBarHasALinkToWithALabelOf(String arg0, String arg1) {
//        Elements links = document.select("a:contains(" + arg1 + ")");
//
//        assertTrue(links.size() > 0);
//
//        for(Element link : links) {
//            assertEquals(arg0, link.attr("href"));
//        }
//    }
//
//    @And("^the client goes to the meetup listed as the \"([^\"]*)\"st button$")
//    public void theClientGoesToTheMeetupListedAsTheStButton(String arg0) throws Throwable {
//        Element button = document.select("button[name=meetupKey]").get(Integer.parseInt(arg0) - 1);
//        Element parent = button.parents().select("form").get(0);
//
//        assertNotNull(parent);
//        assertEquals("POST", parent.attr("method").toUpperCase());
//
//        executePost(parent.attr("action"), button.val());
//    }
//
//    @Then("^the client is redirected to the meetup page$")
//    public void theClientIsRedirectedToTheMeetupPage() {
//        assertEquals(200, result.getResponse().getStatus());
//    }
//}
