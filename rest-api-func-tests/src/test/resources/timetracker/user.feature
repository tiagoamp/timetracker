@USER
Feature: User Resource

  Scenario: Create User
    Given new valid user info
    When Post a request
    Then should create user
    And user should have id and links info

  Scenario: Update User
    Given new valid user info
    When Post a request
    And retrieve user id
    And update user info
    And send a Put request
    Then should return OK
    And should update user
    And user should have id and links info
