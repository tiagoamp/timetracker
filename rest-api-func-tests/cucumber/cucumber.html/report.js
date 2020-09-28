$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("timetracker/category.feature");
formatter.feature({
  "name": "Category Resource",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.scenario({
  "name": "Create Category for User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return Created",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnCreated()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Update Category for User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "update category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.update_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Put request for Category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.send_a_Put_request_for_Category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Delete Category for User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Delete request for Category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.send_a_Delete_request_for_Category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return No Content",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.should_return_No_Content()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Get Categories of User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Get Categories request",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.send_a_Get_Categories_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have an array of results",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_an_array_of_results()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Get Categories of User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@CATEGORY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Get Category by id request",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.send_a_Get_Category_by_id_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.uri("timetracker/timeentry.feature");
formatter.feature({
  "name": "TimeEntry Resource",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@TIME_ENTRY"
    }
  ]
});
formatter.scenario({
  "name": "Create Time entry",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@TIME_ENTRY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid time entry info",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.new_valid_time_entry_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new time entry",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.post_a_request_for_new_time_entry()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return Created",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnCreated()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Update Time Entry for User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@TIME_ENTRY"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid category info",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.new_valid_category_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "new valid time entry info",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.new_valid_time_entry_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new category",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.post_a_request_for_new_category()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve category id",
  "keyword": "And "
});
formatter.match({
  "location": "CategorySteps.retrieve_category_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request for new time entry",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.post_a_request_for_new_time_entry()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve time entry id",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.retrieve_time_entry_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "update time entry info",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.update_time_entry_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Put request for Time entry",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.send_a_Put_request_for_Time_entry()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have category info in time entry",
  "keyword": "And "
});
formatter.match({
  "location": "TimeSteps.should_have_category_info_in_time_entry()"
});
formatter.result({
  "status": "passed"
});
formatter.uri("timetracker/user.feature");
formatter.feature({
  "name": "User Resource",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.scenario({
  "name": "Create User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return Created",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnCreated()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Update User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "update user info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.update_user_info()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Put request",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.send_a_Put_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should update user",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_update_user()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Delete User",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Delete request",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.send_a_Delete_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return No Content",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.should_return_No_Content()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Get All Users",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Get request",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.send_a_Get_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have an array of results",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_an_array_of_results()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Get User by id",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@USER"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "new valid user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserSteps.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserSteps.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "retrieve user id",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.retrieve_user_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "send a Get by id request",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.send_a_Get_by_id_request()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should return OK",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldReturnOK()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
});