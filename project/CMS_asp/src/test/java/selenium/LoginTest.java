package selenium;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

// @CucumberOptions(format = {"json:target/cucumber.json"}, plugin = {"pretty", "html:target/cucumber"})
// @CucumberOptions(plugin = {"pretty", "html:target/cucumber"})
@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:build/target/selenium", "json:build/target/selenium.json"}) // original
//@CucumberOptions(features = {"src/test/resources/selenium"}, format = {"pretty", "html:build/target/selenium", "json:build/target/selenium.json"})
public class LoginTest {
}
