package timetracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

public class UserStepdefs {

    static {
        baseURI = "http://localhost:8080";
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private String userJson = null;
    private ValidatableResponse response = null;


    @Before
    public void cleanDataBase() throws IOException {
        ResponseBody body = given().contentType("application/json")
                .when().get("/user")
                .thenReturn().body();

        ArrayNode arrNode = objectMapper.readValue(body.asString(), ArrayNode.class);
        for (int i = 0; i < arrNode.size(); i++) {
            Integer id = arrNode.get(i).get("id").asInt();
            given().contentType("application/json")
               .when().delete("/user/{id}", id)
               .then().statusCode(HttpStatus.SC_NO_CONTENT);
        }

    }

    @Given("^new user info$")
    public void newUserInfo() {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        userJson = jsonNodes.put("email", "test@test.com")
                            .put("name", "tiagoamp")
                            .put("password", "1234")
                            .toString();
    }

    @When("^Post a request$")
    public void postARequest() {
        response = given().contentType("application/json").body(userJson)
                  .when().post("/user").then();
    }

    @Then("^should create user$")
    public void shouldCreateUser() {
        response.statusCode(SC_CREATED).body("id", notNullValue());
    }



    /*
Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario Outline: Today is or is not Friday
    Given today is "<day>"
    When I ask whether it's Friday yet
    Then I should be told "<answer>"

    Examples:
      | day            | answer |
      | Friday         | TGIF   |
      | Sunday         | Nope   |
      | anything else! | Nope   |

  */


}
