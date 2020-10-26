package timetracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CategorySteps extends GlobalSteps {

    @Before
    public void beforeEachTest() throws IOException {
        cleanDataBase();
    }


    @Given("^new valid category info$")
    public void new_valid_category_info() throws Exception {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        categoryJson = jsonNodes.put("name", "Category Name")
                .put("description", "Category Description")
                .toString();
    }


    @When("^Post a request for new category$")
    public void post_a_request_for_new_category() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        response = given().contentType("application/json").headers("Authorization", bearerToken)
                .body(categoryJson)
                .when().post("/user/{userId}/category", userId).then();
    }

    @When("^retrieve category id$")
    public void retrieve_category_id() throws Exception {
        Integer id = response.extract().body().jsonPath().get("id");
        JsonNode jsonNode = objectMapper.readTree(categoryJson);
        categoryJson = ((ObjectNode) jsonNode).put("id", id).toString();
    }

    @When("^update category info$")
    public void update_category_info() throws Exception {
        JsonNode jsonNode = objectMapper.readTree(categoryJson);
        categoryJson = ((ObjectNode) jsonNode).put("name", "Altered Name").toString();
    }

    @When("^send a Put request for Category$")
    public void send_a_Put_request_for_Category() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long categoryId = objectMapper.readTree(categoryJson).get("id").asLong();
        response = given().contentType("application/json").headers("Authorization", bearerToken)
                .body(categoryJson)
                .when().put("/user/{userId}/category/{categoryId}",userId, categoryId)
                .then();
    }

    @When("^send a Delete request for Category$")
    public void send_a_Delete_request_for_Category() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long categoryId = objectMapper.readTree(categoryJson).get("id").asLong();
        response = given().headers("Authorization", bearerToken)
                .when().delete("/user/{userId}/category/{categoryId}",userId, categoryId).then();
    }

    @When("^send a Get Categories request$")
    public void send_a_Get_Categories_request() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        response = given().headers("Authorization", bearerToken)
                .when().get("/user/{userId}/category",userId).then();
    }

    @When("^send a Get Category by id request$")
    public void send_a_Get_Category_by_id_request() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        Long categoryId = objectMapper.readTree(categoryJson).get("id").asLong();
        response = given().headers("Authorization", bearerToken)
                .when().get("/user/{userId}/category/{categoryId}",userId,categoryId).then();
    }

}
