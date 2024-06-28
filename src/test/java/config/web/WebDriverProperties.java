package config.web;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log
public class WebDriverProperties {

    private final Properties PROPERTIES = new Properties();
    private static final String GLOBAL_DATA_FILE_LOCATION = "/webDriverTest.properties";
    public static String URL_BASE,
            PLATFORM_NAME,
            ENVIRONMENT,
            SYSTEM_ENVIRONMENT,
            PROPERTIES_ENVIRONMENT,
            TESTNG_ENVIRONMENT,
            SYSTEM_CLIENT,
            PROPERTIES_CLIENT,
            TESTNG_CLIENT,
            CLIENT;

    public String MAIN_USERNAME =null;
    public String MAIN_PASSWORD =null;

    public WebDriverProperties(){
        initConfig();
    }

    public void initConfig() {
        try {
            InputStream input;
            input = WebDriverProperties.class.getResourceAsStream(GLOBAL_DATA_FILE_LOCATION);
            PROPERTIES.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PLATFORM_NAME = PROPERTIES.getProperty("webdriver.platformName");
        ENVIRONMENT = PROPERTIES.getProperty("webdriver.env");
        CLIENT = PROPERTIES.getProperty("webdriver.client");

        // system var can be set as $env:webdriver_environment='dev' (windows)
        // system var can be set as $env:webdriver_client='client1' (windows)

        SYSTEM_ENVIRONMENT = System.getenv("webdriver_environment");
        PROPERTIES_ENVIRONMENT = PROPERTIES.getProperty("webdriver.env");

        SYSTEM_CLIENT = System.getenv("webdriver_client");
        PROPERTIES_CLIENT = PROPERTIES.getProperty("webdriver.client");

    }
    public String getUrlBase() {
        String urlProperty = String.format("webdriver.%s.urlBase.%s", getEnvironment(), getClient());
        URL_BASE = PROPERTIES.getProperty(urlProperty) != null ? PROPERTIES.getProperty(urlProperty) : null;

        if (StringUtils.isEmpty(URL_BASE)) {
            urlProperty = String.format(
                    "webdriver.%s.urlBase.%s", getPropertiesEnvironment(), getPropertiesClient());
            URL_BASE = PROPERTIES.getProperty(urlProperty) != null ? PROPERTIES.getProperty(urlProperty) : null;
        }
        Assert.assertTrue(StringUtils.isNotEmpty(URL_BASE), "url base malformation");

        return URL_BASE;
    }

    public String getPlatformName() {
        return PLATFORM_NAME;
    }

    public static String setTestNgEnvironment(String value) {
        TESTNG_ENVIRONMENT = value;
        log.info(TESTNG_ENVIRONMENT);
        return TESTNG_ENVIRONMENT;
    }

    public static String setTestNgClient(String value) {
        TESTNG_CLIENT = value;
        log.info(TESTNG_CLIENT);
        return TESTNG_CLIENT;
    }

    public String getClient() {
        SYSTEM_CLIENT = getSystemClient();
        TESTNG_CLIENT = getTestNgClient();
        PROPERTIES_CLIENT = getPropertiesClient();
        CLIENT =
                StringUtils.isNotEmpty(SYSTEM_CLIENT)
                        ? SYSTEM_CLIENT
                        : StringUtils.isNotEmpty(TESTNG_CLIENT)
                        ? TESTNG_CLIENT
                        : StringUtils.isNotEmpty(PROPERTIES_CLIENT) ? PROPERTIES_CLIENT : null;

        return CLIENT;
    }

    public String getEnvironment() {
        SYSTEM_ENVIRONMENT = getSystemEnvironment();
        TESTNG_ENVIRONMENT = getTestNgEnvironment();
        PROPERTIES_ENVIRONMENT = getPropertiesEnvironment();
        ENVIRONMENT = StringUtils.isNotEmpty(SYSTEM_ENVIRONMENT)
                        ? SYSTEM_ENVIRONMENT
                        : StringUtils.isNotEmpty(TESTNG_ENVIRONMENT)
                        ? TESTNG_ENVIRONMENT
                        : StringUtils.isNotEmpty(PROPERTIES_ENVIRONMENT) ? PROPERTIES_ENVIRONMENT : null;

        return ENVIRONMENT;
    }

    public String getMainUserName(){
        String rawMainUserName = String.format("webdriver.%s.adminUser.%s", getEnvironment(), getClient());
        return PROPERTIES.getProperty(rawMainUserName);
    }

    public String getMainPassword(){
        String rawMainUserName = String.format("webdriver.%s.adminUserPass.%s", getEnvironment(), getClient());
        return PROPERTIES.getProperty(rawMainUserName);
    }

    public String getTestNgEnvironment() {
        return TESTNG_ENVIRONMENT;
    }

    public String getPropertiesEnvironment() {
        return PROPERTIES_ENVIRONMENT;
    }

    public String getSystemEnvironment() {
        log.info("SYSTEM webdriver.env for this test set is " + SYSTEM_ENVIRONMENT);
        return SYSTEM_ENVIRONMENT;
    }

    public String getSystemClient() {
        log.info("SYSTEM webdriver.client for this test set is " + SYSTEM_CLIENT);
        return SYSTEM_CLIENT;
    }

    public String getTestNgClient() {
        return TESTNG_CLIENT;
    }

    public String getPropertiesClient() {
        return PROPERTIES_CLIENT;
    }

}
