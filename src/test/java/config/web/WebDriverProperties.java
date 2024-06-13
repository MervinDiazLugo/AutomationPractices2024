package config.web;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log
public class WebDriverProperties {
    private final Properties PROPERTIES = new Properties();
    private static final String GLOBAL_DATA_FILE_LOCATION = "/webDriverTest.properties";
    public String PLATFORM_NAME = null;
    public String ENVIRONMENT = null;
    public String CLIENT = null;

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

    }
    public String getUrlBase(){
        String rawUrlBase = String.format("webdriver.%s.urlBase.%s", getEnvironment(), getClient());
        return PROPERTIES.getProperty(rawUrlBase);
    }
    public String getPlatformName() {
        return PLATFORM_NAME;
    }

    public String getClient() {
        return CLIENT;
    }

    public String getEnvironment() {
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

}
