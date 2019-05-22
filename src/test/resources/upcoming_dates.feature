Feature: The Upcoming Dates page can be accessed
  Scenario: Client makes call to GET /upcoming
    When the client calls /upcoming
    Then the client receives status code of 200
    And the response is text/html
  Scenario: Client views the accordion
    When the client calls /upcoming
    Then the accordion is made of summary tags
    And the data in the accordion is not default
    And the accordion headers are "Date", "Speaker", "Venue"
  Scenario: Client can use the navigation bar
    When the client calls /upcoming
    Then the navigation bar appears on the page
    And the navigation bar has a link to "/upcoming" with a label of "Admin"
    And the navigation bar has a link to "/upcoming" with a label of "Upcoming Dates"
    And the navigation  bar has a link to "/venue_sheet" with a label of "Google Sheets"
    And the navigation bar has a link to "/log_out" with a label of "Logout"