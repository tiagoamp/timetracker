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

public class UserSteps {

    static {
        baseURI = "http://localhost:8080";
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private String userJson = null;
    private String categoryJson = null;
    private ValidatableResponse response = null;


    @Before
    public void cleanDataBase() throws IOException {
        // get all users
        ResponseBody body = given().when().get("/user")
                .thenReturn().body();
        ArrayNode arrNode = objectMapper.readValue(body.asString(), ArrayNode.class);

        // foreach user
        for (int i = 0; i < arrNode.size(); i++) {
            Integer id = arrNode.get(i).get("id").asInt();

            // get categories of user
            body = given().when().get("/user/{userId}/category", id)
                    .thenReturn().body();
            ArrayNode arrCatNode = objectMapper.readValue(body.asString(), ArrayNode.class);

            // delete categories
            for (int j = 0; j < arrCatNode.size(); j++) {
                Integer catId = arrCatNode.get(j).get("id").asInt();
                given().when().delete("/user/{userId}/category/{catId}", id, catId)
                        .then().statusCode(HttpStatus.SC_NO_CONTENT);
            }

            // delete users
            given().when().delete("/user/{id}", id)
               .then().statusCode(HttpStatus.SC_NO_CONTENT);
        }

    }


    @Given("^new valid user info$")
    public void newUserInfo() {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        userJson = jsonNodes.put("email", "test@test.com")
                            .put("name", "tiagoamp")
                            .put("password", "1234")
                            .toString();
    }

    @Given("^new valid category info$")
    public void new_valid_category_info() throws Exception {
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        categoryJson = jsonNodes.put("name", "Category Name")
                .put("description", "Category Description")
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


    @When("^Post a request for new category$")
    public void post_a_request_for_new_category() throws Exception {
        Long userId = objectMapper.readTree(userJson).get("id").asLong();
        response = given().contentType("application/json").body(categoryJson)
                .when().post("/user/{userId}/category", userId).then();
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

    @Then("^should have an array os Users$")
    public void should_have_an_array_os_Users() throws Exception {
        JsonNode jsonNode = objectMapper.readTree(userJson);
        response.body("$", is(not(emptyArray())));
    }

}
