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
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserSteps extends GlobalSteps {

    @Before
    public void beforeEachTest() throws IOException {
        cleanDataBase();
    }


    @Given("^new valid user info$")
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

    @When("^retrieve user id$")
    public void retrieve_user_id() throws Exception {
        Integer id = response.extract().body().jsonPath().get("id");
        JsonNode jsonNode = objectMapper.readTree(userJson);
        userJson = ((ObjectNode) jsonNode).put("id", id).toString();
    }

    @When("^update user info$")
    public void update_user_info() throws Exception {
        JsonNode jsonNode = objectMapper.readTree(userJson);
        userJson = ((ObjectNode) jsonNode).put("name", "Altered Name").toString();
    }

    @When("^send a Put request$")
    public void send_a_Put_request() throws Exception {
        Long id = objectMapper.readTree(userJson).get("id").asLong();
        response = given().contentType("application/json").body(userJson)
                   .when().put("/user/{id}",id).then();
    }

    @When("^send a Delete request$")
    public void send_a_Delete_request() throws Exception {
        Long id = objectMapper.readTree(userJson).get("id").asLong();
        response = given()
                   .when().delete("/user/{id}",id).then();
    }

    @When("^send a Get request$")
    public void send_a_Get_request() throws Exception {
        response = given()
                   .when().get("/user").then();
    }

    @When("^send a Get by id request$")
    public void send_a_Get_by_id_request() throws Exception {
        Long id = objectMapper.readTree(userJson).get("id").asLong();
        response = given()
                   .when().get("/user/{id}",id).then();
    }


    @Then("^should return Created$")
    public void shouldReturnCreated() {
        response.statusCode(SC_CREATED);
    }

    @Then("^should return OK$")
    public void shouldReturnOK() {
        response.statusCode(SC_OK);
    }

    @Then("^should return No Content$")
    public void should_return_No_Content() {
        response.statusCode(SC_NO_CONTENT);
    }

    @Then("^should have id and links info$")
    public void should_have_id_and_links_info() throws Exception {
        response.body("id", notNullValue()).body("_links", notNullValue());
    }

    @Then("^should update user$")
    public void should_update_user() throws Exception {
        Integer id = objectMapper.readTree(userJson).get("id").asInt();
        String name = objectMapper.readTree(userJson).get("name").asText();
        response.body("id", is(id)).body("name", is(name));
    }

    @Then("^should have an array of results$")
    public void should_have_an_array_of_results() throws Exception {
        response.body("$", is(not(emptyArray())));
    }

}
