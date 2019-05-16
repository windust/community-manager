Feature: The Upcoming Dates page can be accessed
  Scenario: Client makes call to GET /upcoming
    When the client calls /upcoming
    Then the client receives status code of 200
    And the response is text/html
  Scenario: Client reads the page
    When the client calls /upcoming
    Then the accordion is made of summary tags
    And the data in the accordion is not default