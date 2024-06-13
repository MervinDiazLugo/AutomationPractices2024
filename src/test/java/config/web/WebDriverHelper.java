package config.web;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

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



}
