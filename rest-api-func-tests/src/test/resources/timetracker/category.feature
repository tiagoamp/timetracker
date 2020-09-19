@CATEGORY
Feature: Category Resource

  Scenario: Create Category for User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    Then should return Created
    And should have id and links info

  Scenario: Update Category for User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And update category info
    And send a Put request for Category
    Then should return OK
    And should have id and links info

  Scenario: Delete Category for User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And send a Delete request for Category
    Then should return No Content

  Scenario: Get Categories of User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And send a Get Categories request
    Then should return OK
    And should have an array of results

  Scenario: Get Categories of User
    Given new valid user info
    And new valid category info
    When Post a request
    And retrieve user id
    And Post a request for new category
    And retrieve category id
    And send a Get Category by id request
    Then should return OK
    And should have id and links info
