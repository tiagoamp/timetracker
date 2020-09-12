@USER
Feature: User Resource

  Scenario: Create User
    Given new valid user info
    When Post a request
    Then should create user
    And user should have id and links info
