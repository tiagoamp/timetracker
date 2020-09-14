@USER
Feature: User Resource

  Scenario: Create User
    Given new valid user info
    When Post a request
    Then should return Created
    And should have id and links info

  Scenario: Update User
    Given new valid user info
    When Post a request
    And retrieve user id
    And update user info
    And send a Put request
    Then should return OK
    And should update user
    And should have id and links info

  Scenario: Delete User
    Given new valid user info
    When Post a request
    And retrieve user id
    And send a Delete request
    Then should return No Content

  Scenario: Get All Users
    Given new valid user info
    When Post a request
    And send a Get request
    Then should return OK
    And should have an array os Users

  Scenario: Get User by id
    Given new valid user info
    When Post a request
    And retrieve user id
    And send a Get by id request
    Then should return OK
    And should have id and links info

  Scenario: Create Category for User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    Then should return Created
    And should have id and links info