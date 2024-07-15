package myProject.StepsDefinitions;

import config.web.WebDriverProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import config.WebDriverConfig;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static config.web.WebDriverHelper.takeScreenShot;

@Log
public class Hooks {

    public static WebDriver driver;
    public static Scenario scenario = null;

    @Before(value = "@ExamplesWebTest", order = 0)
    public void initOmayoWebDriver() throws Exception {
        WebDriverProperties.setTestNgClient("omayo");
    }

    @Before(value = "@OrangeWebTest", order = 0)
    public void initOrangeWebTest() throws Exception {
        WebDriverProperties.setTestNgClient("orange");
    }

    @Before(value = "@WebTesting")
    public void initWebDriver(Scenario scenario) throws Exception {
        this.scenario = scenario;
        log.info("***************************************************");
        log.info("[ Scenario ] - " + scenario.getName());
        log.info("***************************************************");
        driver =  WebDriverConfig.initSeleniumConfig();
    }

    @After(value = "@WebTesting")
    public void tearDown(Scenario scenario) throws IOException {
        if (!StringUtils.equalsIgnoreCase(
                scenario.getStatus().toString(),"PASSED")) {
            log.info("[ Scenario ] - " + scenario.getName() + " - " + scenario.getStatus().toString());
            takeScreenShot(driver, scenario);
        }
        driver.quit();
    }
}
