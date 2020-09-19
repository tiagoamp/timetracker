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

}
