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
  "name": "new user info",
  "keyword": "Given "
});
formatter.match({
  "location": "UserStepdefs.newUserInfo()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Post a request",
  "keyword": "When "
});
formatter.match({
  "location": "UserStepdefs.postARequest()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "should create user",
  "keyword": "Then "
});
formatter.match({
  "location": "UserStepdefs.shouldCreateUser()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user should have id and links info",
  "keyword": "And "
});
formatter.match({
  "location": "UserStepdefs.user_should_have_id_and_links_info()"
});
formatter.result({
  "status": "passed"
});
});