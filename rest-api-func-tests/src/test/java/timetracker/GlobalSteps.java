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
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.apache.http.Header;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    static List<Integer> preRegisteredUserIds = Arrays.asList(555, 888, 999);

    static String userJson = null;
    static String categoryJson = null;
    static String entryJson = null;

    static String bearerToken = null;


    protected void authenticateAdmin() throws IOException {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        String adminJson = jsonNodes.put("email", "ADMIN@EMAIL.COM.BR")
                .put("password", "1234").toString();
        ValidatableResponse response = given().contentType("application/json")
                .body(adminJson)
                .when().post("/timetracker/auth")
                .then();
        String jwt = response.extract().body().jsonPath().get("token");
        bearerToken = "Bearer " + jwt;
    }

    protected void cleanDataBase() throws IOException {
        if (bearerToken == null) authenticateAdmin();

        // get all users
        ResponseBody body = given().headers("Authorization", bearerToken)
                .when().get("/user")
                .thenReturn().body();
        ArrayNode arrNode = objectMapper.readValue(body.asString(), ArrayNode.class);

        // foreach user
        for (int i = 0; i < arrNode.size(); i++) {
            Integer userId = arrNode.get(i).get("id").asInt();
            if (preRegisteredUserIds.contains(userId.intValue()))
                continue;

            // get time entries of user
            body = given().header("Authorization", bearerToken)
                    .when().get("/user/{userId}/time", userId)
                    .thenReturn().body();
            ArrayNode arrTimeNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete time entries
            for (int j = 0; j < arrTimeNode.size(); j++) {
                Integer entryId = arrTimeNode.get(j).get("id").asInt();
                given().header("Authorization", bearerToken)
                        .when().delete("/user/{userId}/time/{timeId}", userId, entryId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // get categories of user
            body = given().header("Authorization", bearerToken)
                    .when().get("/user/{userId}/category", userId)
                    .thenReturn().body();
            ArrayNode arrCatNode = objectMapper.readValue(body.asString(), ArrayNode.class);
            // delete categories
            for (int k = 0; k < arrCatNode.size(); k++) {
                Integer catId = arrCatNode.get(k).get("id").asInt();
                given().header("Authorization", bearerToken)
                        .when().delete("/user/{userId}/category/{catId}", userId, catId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // delete users
            given().header("Authorization", bearerToken)
                    .when().delete("/user/{id}", userId)
                    .then().statusCode(HttpStatus.SC_NO_CONTENT);
        }

    }

}
