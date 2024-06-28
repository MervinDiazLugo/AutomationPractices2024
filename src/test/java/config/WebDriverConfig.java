package config;

import config.web.WebDriverFactory;
import config.web.WebDriverProperties;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

@Log
public class WebDriverConfig {
    private static WebDriverProperties webDriverProperties = new WebDriverProperties();
    public static WebDriver initSeleniumConfig() throws Exception {

        String platform = webDriverProperties.getPlatformName();
        WebDriver driver;

        log.info("**********************************************************************************************");
        log.info("[ POM Configuration ] - Read the basic properties configuration from: ../webDriverTest.properties");
        log.info("[ POM Configuration ] - Browser: " + platform);
        log.info("*********************************************************************************************");

        /** **** Load the driver ****** */
        driver = WebDriverFactory.createNewDriver(
                webDriverProperties.getPlatformName(),
                webDriverProperties.getUrlBase());

        return driver;
    }

}
