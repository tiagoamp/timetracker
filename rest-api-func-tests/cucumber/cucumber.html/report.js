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
  "name": "should create user",
  "keyword": "Then "
});
formatter.match({
  "location": "UserSteps.shouldCreateUser()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.user_should_have_id_and_links_info()"
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
  "name": "user should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserSteps.user_should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
});