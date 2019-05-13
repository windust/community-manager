Feature: The Upcoming Dates page can be accessed
  Scenario: Client makes call to GET /upcoming
    When the client calls /upcoming
    Then the client receives status code of 200
    And the client receives server version 1.0