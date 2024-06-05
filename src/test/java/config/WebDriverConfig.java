package config;

import config.web.WebDriverFactory;
import config.web.WebDriverProperties;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverConfig {

    public static WebDriver initSeleniumConfig() throws Exception {
        Logger log = LoggerFactory.getLogger(WebDriverConfig.class);
        WebDriverProperties webDriverProperties = new WebDriverProperties();

        String platform = webDriverProperties.getPlatformName();
        String urlBase = webDriverProperties.getUrlBase();
        WebDriver driver;

        log.info("**************************************************************************************************");
        log.info("[ POM Configuration ] - Read the basic properties configuration from: ../webDriverTest.properties");
        log.info("[ POM Configuration ] - Browser: " + platform);
        log.info("*********************************************************************************************");

        /** **** Load the driver ****** */
        driver = WebDriverFactory.createNewDriver(platform, urlBase);

        return driver;
    }

}
