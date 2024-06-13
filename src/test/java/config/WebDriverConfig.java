package config;

import config.web.WebDriverFactory;
import config.web.WebDriverProperties;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

@Log
public class WebDriverConfig {

    public static WebDriver initSeleniumConfig() throws Exception {
        WebDriverProperties webDriverProperties = new WebDriverProperties();

        String platform = webDriverProperties.getPlatformName();
        String urlBase = webDriverProperties.getUrlBase();
        WebDriver driver;

        log.info("**********************************************************************************************");
        log.info("[ POM Configuration ] - Read the basic properties configuration from: ../webDriverTest.properties");
        log.info("[ POM Configuration ] - Browser: " + platform);
        log.info("*********************************************************************************************");

        /** **** Load the driver ****** */
        driver = WebDriverFactory.createNewDriver(platform, urlBase);

        return driver;
    }

}
