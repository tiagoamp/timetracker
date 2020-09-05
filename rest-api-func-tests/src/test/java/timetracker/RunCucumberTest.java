package timetracker;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:cucumber/cucumber.html", "json:cucumber/cucumber.json", "junit:cucumber/cucumber.xml"})
public class RunCucumberTest {

}
