package myProject.runners.web;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        tags = "@OrangeWebTest and not @IGNORE",
        features = "src/test/resources/myProject/WebTest/Orange",
        glue = "myProject.StepsDefinitions",
        plugin = {
                "pretty",
                "html:test-output",
                "json:target/cucumber/cucumber.json",
                "html:target/cucumber-html-report.html"
        })
public class OrangeWebRunner extends AbstractTestNGCucumberTests{
}
