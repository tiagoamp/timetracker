$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("timetracker/user.feature");
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
  "name": "should have an array os Users",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.should_have_an_array_os_Users()"
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
formatter.scenario({
  "name": "Create Category for User",
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
  "location": "UserSteps.new_valid_category_info()"
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
  "location": "UserSteps.post_a_request_for_new_category()"
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
});