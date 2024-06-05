package myProject.StepsDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import config.WebDriverConfig;
import org.openqa.selenium.WebDriver;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void initWebDriver(Scenario scenario) throws Exception {
        driver =  WebDriverConfig.initSeleniumConfig();
    }


    @After
    public void tearDown(){
        driver.quit();
    }
}
