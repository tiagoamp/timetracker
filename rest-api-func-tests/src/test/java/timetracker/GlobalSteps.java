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

public class GlobalSteps {

    static {
        baseURI = "http://localhost:8080";
    }

    protected ObjectMapper objectMapper = new ObjectMapper();

    static ValidatableResponse response = null;

    static String userJson = null;
    static String categoryJson = null;
    static String entryJson = null;


    protected void cleanDataBase() throws IOException {
        // get all users
        ResponseBody body = given().when().get("/user")
                .thenReturn().body();
        ArrayNode arrNode = objectMapper.readValue(body.asString(), ArrayNode.class);

        // foreach user
        for (int i = 0; i < arrNode.size(); i++) {
            Integer userId = arrNode.get(i).get("id").asInt();

            // get time entries of user
            body = given().when().get("/user/{userId}/time", userId)
                    .thenReturn().body();
            ArrayNode arrTimeNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete time entries
            for (int j = 0; j < arrTimeNode.size(); j++) {
                Integer entryId = arrTimeNode.get(j).get("id").asInt();
                given().when().delete("/user/{userId}/time/{timeId}", userId, entryId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // get categories of user
            body = given().when().get("/user/{userId}/category", userId)
                    .thenReturn().body();
            ArrayNode arrCatNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete categories
            for (int k = 0; k < arrCatNode.size(); k++) {
                Integer catId = arrCatNode.get(k).get("id").asInt();
                given().when().delete("/user/{userId}/category/{catId}", userId, catId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // delete users
            given().when().delete("/user/{id}", userId)
                    .then().statusCode(HttpStatus.SC_NO_CONTENT);
        }

    }

}
