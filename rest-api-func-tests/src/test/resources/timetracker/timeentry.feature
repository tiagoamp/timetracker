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

  Scenario: Update Time Entry for User
    Given new valid user info
    And new valid category info
    And new valid time entry info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And Post a request for new time entry
    And retrieve time entry id
    And update time entry info
    And send a Put request for Time entry
    Then should return OK
    And should have id and links info
    And should have category info in time entry

  Scenario: Delete Time Entry for User
    Given new valid user info
    And new valid category info
    And new valid time entry info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And Post a request for new time entry
    And retrieve time entry id
    And send a Delete request for Time Entry
    Then should return No Content