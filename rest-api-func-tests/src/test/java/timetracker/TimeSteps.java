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

public class TimeSteps extends GlobalSteps {

    @Before
    public void beforeEachTest() throws IOException {
        cleanDataBase();
    }


    @Given("^new valid time entry info$")
    public void new_valid_time_entry_info() throws Exception {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        entryJson = jsonNodes.put("startTime", "2020-09-17T13:56:39.492")
                .put("annotations", "annot")
                .toString();
    }

    @When("^Post a request for new time entry$")
    public void post_a_request_for_new_time_entry() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long categoryId = objectMapper.readTree(categoryJson).get("id").asLong();
        JsonNode jsonNode = objectMapper.readTree(entryJson);
        entryJson = ((ObjectNode) jsonNode).put("userId", userId)
                .put("categoryId", categoryId)
                .toString();
        response = given().contentType("application/json").body(entryJson)
                .when().post("user/{userId}/time", userId).then();
    }

    @When("^retrieve time entry id$")
    public void retrieve_time_entry_id() throws Exception {
        Integer id = response.extract().body().jsonPath().get("id");
        JsonNode jsonNode = objectMapper.readTree(entryJson);
        entryJson = ((ObjectNode) jsonNode).put("id", id).toString();
    }

    @When("^update time entry info$")
    public void update_time_entry_info() throws Exception {
        JsonNode jsonNode = objectMapper.readTree(entryJson);
        entryJson = ((ObjectNode) jsonNode).put("annotations", "Altered Annotation").toString();
    }

    @When("^send a Put request for Time entry$")
    public void send_a_Put_request_for_Time_entry() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long timeId = objectMapper.readTree(entryJson).get("id").asLong();
        response = given().contentType("application/json").body(entryJson)
                .when().put("/user/{userId}/time/{timeId}",userId, timeId)
                .then();
    }

    @When("^send a Delete request for Time Entry$")
    public void send_a_Delete_request_for_Time_Entry() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long timeId = objectMapper.readTree(entryJson).get("id").asLong();
        response = given()
                .when().delete("/user/{userId}/time/{timeId}",userId, timeId).then();
    }

    @When("^send a Get request for User Time entries$")
    public void send_a_Get_request_for_User_Time_entries() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        response = given()
                .when().get("/user/{userId}/time", userId).then();
    }

    @When("^send a Get request by id of Time entry$")
    public void send_a_Get_request_by_id_of_Time_entry() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long timeId = objectMapper.readTree(entryJson).get("id").asLong();
        response = given()
                .when().get("/user/{userId}/time/{timeId}",userId, timeId).then();
    }


    @Then("^should have category info in time entry$")
    public void should_have_category_info_in_time_entry() throws Exception {
        response.body("categoryId", notNullValue()).body("categoryName", notNullValue());
    }

}
