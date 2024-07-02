package config.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Log
public class WebDriverFactory {
    final static String DOWNLOAD_FOLDER = getCurrentPath() +
            "\\src\\test\\resources\\downloads";
    final static String CHROMEDRIVER_PATH = getCurrentPath() +
            "\\src\\test\\resources\\bin\\windows32\\chromedriver.exe";

    final static String CHROME_BINARY = getCurrentPath()
            + "\\src\\test\\resources\\bin\\chrome-win64";

    public static WebDriver createNewDriver(String platform, String urlBase) throws Exception {
        WebDriver driver;

        if (!"CHROME_LOCAL".equalsIgnoreCase(platform)) {
            log.info("Creating session using boni garcia");
        }
        if ("FIREFOX".equalsIgnoreCase(platform)) {
            WebDriverManager.firefoxdriver().clearResolutionCache().forceDownload().setup();
            driver = new FirefoxDriver();
        } else if ("CHROME".equalsIgnoreCase(platform)) {
            driver = chrome();
        } else if ("CHROME_LOCAL".equalsIgnoreCase(platform)) {
            driver = chromeLocal();
        } else if(StringUtils.equalsIgnoreCase(platform, "CHROME_LOCAL_BINARIES")){
            driver = chromeLocalBinaries();
        }else if(StringUtils.equalsIgnoreCase(platform, "CHROME_SELENIUM_GRIP")){
            driver = seleniumGrid();
        }else {
            log.info("The Driver is not selected properly, invalid name: " + platform);
            return null;
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(urlBase);
        return driver;
    }

    public static WebDriver seleniumGrid() throws MalformedURLException {
        log.info("Creating chrome from selenium grid...");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("platformName", "Windows");
        options.setCapability("browserName", "chrome");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return new RemoteWebDriver(new URL("http://localhost:4444/"), options);
    }
    public static WebDriver chrome(){
        WebDriverManager.chromedriver().clearResolutionCache().forceDownload().setup();
        Map<String, Object> prefs = new HashMap<String, Object>();
        ChromeOptions options = new ChromeOptions();
        prefs.put("download.default_directory", getCurrentPath() + DOWNLOAD_FOLDER);
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return new ChromeDriver(options);
    }
    public static WebDriver chromeLocal(){
        System.setProperty("webdriver.chrome.driver",  CHROMEDRIVER_PATH);
        Map<String, Object> prefs = new HashMap<String, Object>();
        ChromeOptions options = new ChromeOptions();
        prefs.put("download.default_directory", DOWNLOAD_FOLDER);
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return new ChromeDriver(options);
    }
    public static WebDriver chromeLocalBinaries(){
        log.info("Creating chrome binaries local session...");
        ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROME_BINARY);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
        return new ChromeDriver();
    }

    public static String getCurrentPath() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
