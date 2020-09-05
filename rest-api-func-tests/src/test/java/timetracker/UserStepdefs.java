package timetracker;

import io.cucumber.java8.En;
import static org.junit.jupiter.api.Assertions.*;

public class UserStepdefs implements En {

    public UserStepdefs() {
        Given("^new user info$", () -> {
            System.out.println("ueaba");
            assertTrue(true);
        });
        When("^Post a request$", () -> {
        });
        Then("^should create user$", () -> {
        });
    }

}
