@USER
Feature: User Resource

  Scenario: Create User
    Given new user info
    When Post a request
    Then should create user
    And user should have id and links info
