Feature The Available Dates page can be accessed
  Background:
    Given the following Venues exist:
      | name        | token           | requested date |
      | Disney      | disney-123      | 2029-01-14     |
      | Smart Sheet | smart-sheet-123 | 2029-02-19     |
      | Expedia     | expedia-123     | 2011-02-11     |
    And the following Meetups exist:
      | date       | speaker | venue   |
      | 2029-01-14 | Freddy  |         |
      | 2029-02-19 | Nimret  |         |
      | 2029-02-19 | Josh    | Expedia |
      | 2029-04-16 | Kirk    | Disney  |

  Scenario: Venue makes call to GET /venue
    When a Venue goes to '/venue' with the token of 'disney-123'
    Then Happy Result

  Scenario: Non-Venue makes call to GET /venue
    When a User goes tp '/venue' with the token of 'does-not-exist'
    Then Sad Result

  Scenario: Venue clicks yes in banner then 'yes, but no food' in modal
    When a Venue goes to '/venue' with the token of 'disney-123'
    And the Venue clicks 'yes' in the banner
    Then the modal shows
    When the Venue clicks 'Yes, but no food' then venue will be signed up but not for food

  Scenario: Venue clicks yes in banner then 'yes, and food' in modal
    When a Venue goes to '/venue' with the token of 'disney-123'
    And the Venue clicks 'yes' in the banner
    Then the modal shows
    When the Venue clicks 'Yes, and food' then venue will be signed up for food and venue

  Scenario: Venue clicks yes in banner then no in modal
    When a Venue goes to '/venue' with the token of 'disney-123'
    And the Venue clicks 'yes' in the banner
    Then the modal shows
    When the Venue clicks 'cancel' then nothing will happen

  Scenario: Venue clicks no in banner
    When a Venue goes to '/venue' with the token of 'disney-123'
    And the Venue clicks 'no' in the banner
    Then nothing will be recorded