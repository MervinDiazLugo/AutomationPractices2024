package myProject.StepsDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import config.WebDriverConfig;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

@Log
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
