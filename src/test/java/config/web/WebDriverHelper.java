package config.web;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.SkipException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static config.web.WebDriverFactory.getCurrentPath;

@Log
public class WebDriverHelper extends WebDriverDataManagementHelper{

    final Duration EXPLICIT_TIMEOUT = Duration.ofSeconds(15);

    public HashMap<String, String> windowsHandle = new HashMap<>();


    public WebElement getElement(WebDriver driver, By loc){
        return isWebElementDisplayed(driver, loc)
                ? driver.findElement(loc)
                : null;
    }

    public List<WebElement> getElements(WebDriver driver, By loc){
        return isWebElementDisplayed(driver, loc)
                ? driver.findElements(loc)
                : null;
    }

    public boolean isWebElementDisplayed(WebDriver driver, By element) {
        boolean isDisplayed;
        try {
            log.info(String.format("Waiting Element: %s", element));
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            isDisplayed = false;
            log.info(String.valueOf(e));
        }
        log.info(String.format("%s visibility is: %s", element, isDisplayed));
        return isDisplayed;
    }


    public Alert isAlertPresent(WebDriver driver){
        Alert simpleAlert = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            wait.until(ExpectedConditions.alertIsPresent());
            simpleAlert = driver.switchTo().alert();
            log.info("Alert is present");
        } catch (Exception e) {
            log.info("Alert not present");
        }
        return simpleAlert;
    }

    public void getWindowsHandle(WebDriver driver, String windowsName) {
        boolean alreadyExist;
        sleep(10);
        if (windowsHandle.containsKey(windowsName)) {
            driver.switchTo().window(windowsHandle.get(windowsName));
            log.info(String.format("I go to Windows: %s with value: %s ",
                            windowsName, windowsHandle.get(windowsName)));
        } else {
            for (String winHandle : driver.getWindowHandles()) {
                for (String entry : windowsHandle.keySet()) {
                    String value = windowsHandle.get(entry.trim());
                    alreadyExist = StringUtils.equalsIgnoreCase(value, winHandle);
                    if (!alreadyExist) {
                        windowsHandle.put(windowsName, winHandle);
                        log.info("The New window "
                                        + windowsName
                                        + " was saved in scenario with value "
                                        + windowsHandle.get(windowsName));
                        driver.switchTo().window(winHandle);
                        break;
                    }
                }
            }
        }
    }


    public void sleep(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Actions createActionBuilder(WebDriver driver){
        return new Actions(driver);
    }


    public Action dragAndDropToElement(WebDriver driver, By sourceLoc, By targetLoc){
        return createActionBuilder(driver)
                .dragAndDrop(getElement(driver,sourceLoc), getElement(driver,targetLoc))
                .build();
    }

    public Action moveToElement(WebDriver driver, By loc){
        return createActionBuilder(driver)
                .moveToElement(getElement(driver, loc))
                .build();
    }

    public Action moveToElementAndClick(WebDriver driver, By loc){
        return createActionBuilder(driver)
                .moveToElement(getElement(driver, loc))
                .click(getElement(driver, loc))
                .build();
    }

    /**
     * click using generic xpath
     *
     * @param locator text used as reference
     */
    public void click(WebDriver driver, By locator) {
        WebElement elem = getElement(driver, locator);
        if (elem != null) {
            elem.click();
            log.info(locator + " clicked");
        } else {
            throw new SkipException("Locator was not present " + locator);
        }
    }

    /**
     * click using generic xpath
     *
     * @param locator text used as reference
     */
    public void jsSendKeys(WebDriver driver,By locator, String value) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement elem = getElement(driver, locator);
        if (elem != null) {
            jse.executeScript(String.format("arguments[0].value='%s';", value), elem);
            log.info(locator + " value set by Js " + value);
        } else {
            throw new SkipException("Locator was not present " + locator);
        }
    }

    public void scrollToElement(WebDriver driver, By locator) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        log.info("Scrolling to element: " + locator.toString());
        WebElement elem = getElement(driver, locator);
        if (elem != null) {
            jse.executeScript("arguments[0].scrollIntoView();", elem);
        }else{
            throw new SkipException("Locator was not present " + locator);
        }
    }

    public void scrollToElement(WebDriver driver, WebElement elem) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if (elem != null) {
            log.info("Scrolling to element: " + elem.toString());
            jse.executeScript("arguments[0].scrollIntoView();", elem);
        }else{
            throw new SkipException("Locator was not present");
        }
    }

    /**
     * click using javascript
     *
     * @param element element used as reference
     */
    public void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        if(element!=null){
            log.info("jsClick: Scrolling to element: " + element.toString());
            executor.executeScript("arguments[0].click();", element);
        }else{
            throw new SkipException("Locator was not present");
        }
    }
    /**
     * click using javascript
     *
     * @param locator element used as reference
     */
    public void jsClick(WebDriver driver, By locator) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        log.info("looking to element: " + locator.toString());
        WebElement elem = driver.findElement(locator) != null ? driver.findElement(locator): null;
        if (elem != null) {
            jse.executeScript("arguments[0].click();", elem);
        }else{
            throw new SkipException("jsClick: Locator was not present " + locator);
        }
    }

    /**
     * click using javascript
     *
     * @param element element used as reference
     */
    public void setAttribute(WebDriver driver, WebElement element, String key, String value) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        if (element != null) {
            executor.executeScript(String.format("arguments[0].setAttribute('%s', '%s');", key, value), element);
        } else {
            throw new SkipException("setAttribute: Locator was not present ");
        }
        // executor.executeScript("arguments[0].setAttribute('class', 'multiselect-item dropdown-item
        // form-check active');", element);
    }

    /**
     * click using javascript
     *
     * @param locator element used as reference
     */
    public void setAttribute(WebDriver driver, By locator, String key, String value) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebElement elem = driver.findElement(locator) != null ? driver.findElement(locator): null;
        if (elem != null) {
            executor.executeScript(String.format("arguments[0].setAttribute('%s', '%s');", key, value), elem);
        } else {
            throw new SkipException("setAttribute: Locator was not present " + locator);
        }
    }


    public void waitPageCompletelyLoaded(WebDriver driver) {
        String GetActual = driver.getCurrentUrl();
        log.info(String.format("Checking if %s page is loaded.", GetActual));
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(EXPLICIT_TIMEOUT)
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

    }


    /**
     * Create a table with parameters given on feature step.
     *
     * @param table is a list with parameters given on step.
     */
    public DataTable createDataTable(List<List<String>> table) {
        DataTable data;
        data = DataTable.create(table);
        log.info(data.toString());
        return data;
    }


    public void selectOptionDropdownByText(WebDriver driver, By locator, String optionToSelect) {
        log.info("Select option: " + optionToSelect + " by text");
        WebElement selectElm = getElement(driver, locator);
        if (selectElm != null) {
            Select select = new Select(selectElm);
            List<WebElement> selectListOpt = select.getOptions();
            Optional<WebElement> matchingOption = selectListOpt.stream()
                    .filter(option -> option.getText().equals(optionToSelect))
                    .findFirst();
            matchingOption.ifPresent(elm -> elm.click());

        } else {
            throw new SkipException("Locator was not present " + locator);
        }

    }

    public void selectOptionDropdownByValue(WebDriver driver, By locator, String option) {
        log.info(String.format("Waiting Element: %s", locator));
        WebElement elem = getElement(driver, locator);
        if (elem != null) {
            Select opt = new Select(elem);
            log.info("Select option: " + option + " by value");
            opt.selectByValue(option);
        } else {
            throw new SkipException("Locator was not present " + locator);
        }
    }

    public void selectOptionDropdownByIndex(WebDriver driver, By locator, int option) {
        log.info(String.format("Waiting Element: %s", locator));
        WebElement elem = getElement(driver, locator);
        if (elem != null) {
            Select opt = new Select(elem);
            log.info("Select option: " + option + " by index");
            opt.selectByIndex(option);
        } else {
            throw new SkipException("Locator was not present " + locator);
        }
    }


    /**
     * click using generic xpath
     *
     * @param locator text used as reference
     */
    public String getAttribute(WebElement locator, String attribute) {
        String value = locator.getAttribute(attribute);
        log.info(locator + " return the value " + value);
        return value;
    }

    public void takeScreenShot(WebDriver driver) throws IOException {
        log.info("Saving screen shot");
        File destFile = new File(getCurrentPath() + "/target/screenshots/"
                + UUID.randomUUID()
                + ".jpg");
        FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destFile);
    }

    public void takeScreenShot(WebDriver driver, Scenario scenario) throws IOException {
        log.info("Saving screen shot");
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        File destFile = new File(getCurrentPath() + "/target/screenshots/"
                + scenario.getName()
                + scenario.getId() + ".jpg");

        scenario.attach(screenshot, "jpg", scenario.getId()
                + "_"
                + scenario.getName().replace(" ", "_")
                + ".jpg");
        FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destFile);
    }

}
