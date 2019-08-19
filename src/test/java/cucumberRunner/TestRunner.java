package cucumberRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(features = { "src/test/resources/features" }, 
glue = { "com.hostelworld.stepDefinition" }
//,tags = {"@logtest1" }
)

public class TestRunner {

	
}