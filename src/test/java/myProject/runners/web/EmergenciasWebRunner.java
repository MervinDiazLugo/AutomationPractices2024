package myProject.runners.web;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        tags = "@EmergenciasWebTest and not @IGNORE",
        features = "src/test/resources/myProject/WebTest/Emergencias",
        glue = "myProject.StepsDefinitions",
        plugin = {
                "pretty",
                "html:test-output",
                "json:target/cucumber/cucumber.json",
                "html:target/cucumber-html-report.html"
        })
public class EmergenciasWebRunner extends AbstractTestNGCucumberTests{
}
