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
    public void cleanDataBase() throws IOException {
        // get all users
        ResponseBody body = given().when().get("/user")
                .thenReturn().body();
        ArrayNode arrNode = objectMapper.readValue(body.asString(), ArrayNode.class);

        // foreach user
        for (int i = 0; i < arrNode.size(); i++) {
            Integer id = arrNode.get(i).get("id").asInt();

            // get time entries of user
            body = given().when().get("/time/user/{userId}", id)
                    .thenReturn().body();
            ArrayNode arrTimeNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete time entries
            for (int j = 0; j < arrTimeNode.size(); j++) {
                Integer entryId = arrTimeNode.get(j).get("id").asInt();
                given().when().delete("/time/{timeId}", entryId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // get categories of user
            body = given().when().get("/user/{userId}/category", id)
                    .thenReturn().body();
            ArrayNode arrCatNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete categories
            for (int k = 0; k < arrCatNode.size(); k++) {
                Integer catId = arrCatNode.get(k).get("id").asInt();
                given().when().delete("/user/{userId}/category/{catId}", id, catId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // delete users
            given().when().delete("/user/{id}", id)
                    .then().statusCode(HttpStatus.SC_NO_CONTENT);
        }

    }


    @Given("^new valid time entry info$")
    public void new_valid_time_entry_info() throws Exception {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        // {"userId":1,"categoryId":10,"startTime":[2020,9,17,14,25,58,315142000]}
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
                .when().post("/time").then();
    }

}
