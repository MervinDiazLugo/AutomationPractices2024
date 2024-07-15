package myProject.runners.web;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        tags = "@ExamplesWebTest and not @IGNORE",
        features = "src/test/resources/myProject/WebTest/Examples",
        glue = "myProject.StepsDefinitions",
        plugin = {
                "pretty",
                "html:test-output",
                "json:target/cucumber/examples-cucumber.json",
                "html:target/cucumber-html-report.html"
        })
public class ExamplesWebRunner extends AbstractTestNGCucumberTests{
}
