@TIME_ENTRY
Feature: TimeEntry Resource

  Scenario: Create Time entry
    Given new valid user info
    And new valid category info
    And new valid time entry info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And Post a request for new time entry
    Then should return Created
    And should have id and links info