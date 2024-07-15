package myProject.runners.web;
import config.web.WebDriverProperties;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        tags = "@EmergenciasWebTest and not @IGNORE",
        features = "src/test/resources/myProject/WebTest/Emergencias",
        glue = "myProject.StepsDefinitions",
        plugin = {
                "pretty",
                "html:test-output",
                "json:target/cucumber/emergencias-cucumber.json",
                "html:target/cucumber-html-report.html"
        })
@Log
public class EmergenciasWebRunnerWithParams extends AbstractTestNGCucumberTests{
        @BeforeTest
        @Parameters({"webdriver.env", "webdriver.client"})
        public void beforeSuite(@Optional("null") String environment, @Optional("null") String client) {
                log.info("TestNG webdriver.env for this test set is " + environment);
                log.info("TestNG webdriver.client for this test set is " + client);
                WebDriverProperties.setTestNgEnvironment(environment);
                WebDriverProperties.setTestNgClient(client);
        }
}
