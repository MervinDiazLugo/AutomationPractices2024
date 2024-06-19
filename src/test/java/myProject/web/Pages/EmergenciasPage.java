package myProject.web.Pages;

import config.web.WebDriverHelper;
import io.cucumber.datatable.DataTable;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;

import java.util.Collections;
import java.util.List;

import static myProject.web.PageObjects.EmergenciasPageObjects.*;


@Log
public class EmergenciasPage extends WebDriverHelper {

    public void waitForFirstStepElements(WebDriver driver) {
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Conocé el precio de tu plan"));
        Assert.assertTrue(isWebElementDisplayed(driver, titleLoc), "title element was not visible on page");

        Assert.assertTrue(isWebElementDisplayed(driver, SUB_TITLE_LOC), "Sub title element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, FULL_NAME_LOC), "Full name element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, PROVINCE_LOC), "Select Province element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, AREA_CODE_LOC), "Area code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, PHONE_NUMBER_LOC), "Phone number element was not visible on page");
    }


    public void setFirstStepTextBoxes(WebDriver driver, List<List<String>> table) {
        DataTable data = createDataTable(table);
        if (data != null) {
            // AtomicInteger i = new AtomicInteger(1);
            data.cells().forEach(value -> {
                        //create variables as columns you have.
                        String KEY = value.get(0);
                        String VALUE = value.get(1);
                        log.info(KEY);
                        log.info(VALUE);
                        try {
                            //Logic
                            if (StringUtils.containsIgnoreCase(KEY, "Nombre")) {
                                jsSendKeys(driver, FULL_NAME_LOC, VALUE);
                                click(driver, FULL_NAME_LOC);

                            } else if (StringUtils.containsIgnoreCase(KEY, "Cód. área")) {
                                jsSendKeys(driver,AREA_CODE_LOC, VALUE);
                                click(driver, AREA_CODE_LOC);

                            } else if (StringUtils.containsIgnoreCase(KEY, "Celular")) {
                                jsSendKeys(driver,PHONE_NUMBER_LOC, VALUE);
                                click(driver, PHONE_NUMBER_LOC);

                            } else if (StringUtils.containsIgnoreCase(KEY, "Provincia")) {
                                selectOptionDropdownByText(driver, PROVINCE_LOC, VALUE);

                            } else if (StringUtils.containsIgnoreCase(KEY, "Cantidad de adultos")) {
                                setInputValues(driver, DIV_ADULTS_QUANTITY, KEY, VALUE);

                            } else if (StringUtils.containsIgnoreCase(KEY, "Menores de 12 años")) {
                                setInputValues(driver, DIV_CHILDREN_QUANTITY, KEY, VALUE);
                            }


                        } catch (NullPointerException e) {
                            log.info(String.format("Path specified on table doesn't exist: %s", KEY));
                            throw new SkipException(String.format("Path specified on table doesn't exist: %s", KEY));
                        }

                    });
        }


    }

    public void clickOnButtons(WebDriver driver, String tag) {
        if (StringUtils.containsIgnoreCase(tag, "Cotizá")) {
            click(driver, FIRST_STEP_BUTTON_LOC);
            waitPageCompletelyLoaded(driver);
            log.info("waiting second step loaded");
        }
        if (StringUtils.containsIgnoreCase(tag, "Siguiente")) {
            click(driver, SECOND_STEP_BUTTON_LOC);
            waitPageCompletelyLoaded(driver);
            log.info("waiting second step loaded");
        }
    }


    public void waitForSecondStepElements(WebDriver driver) {
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Elegí el plan que más te conviene"));
        Assert.assertTrue(isWebElementDisplayed(driver, titleLoc), "title element was not visible on page");

        Assert.assertTrue(
                isWebElementDisplayed(driver, DIV_ADULTS_QUANTITY),
                "Adults quantity element was not visible on page");

        Assert.assertTrue(
                isWebElementDisplayed(driver, DIV_CHILDREN_QUANTITY),
                "Children quantity element was not visible on page");
    }

    public void setInputValues(WebDriver driver, By locator, String key, String valueToBeSet) {
        if (isWebElementDisplayed(driver, locator)) {
            WebElement divElements = getElement(driver, locator);
            WebElement removeElem = divElements.findElement(STEPPER_REMOVE_BUTTON);
            WebElement addElem = divElements.findElement(STEPPER_ADD_BUTTON);
            WebElement valueElem = null;

            if (StringUtils.containsIgnoreCase(key, "Cantidad de adultos")) {
                valueElem = divElements.findElement(STEPPER_ADULTS_VALUE_TEXT_BOX);
            } else if (StringUtils.containsIgnoreCase(key, "Menores de 12 años")) {
                valueElem = divElements.findElement(STEPPER_CHILDREN_VALUE_TEXT_BOX);
            }

            if (!removeElem.isDisplayed() && !addElem.isDisplayed() && !valueElem.isDisplayed()) {
                Assert.fail(String.format("Select %s elements are not present", key));
            }

            String currentValue = getAttribute(valueElem, "value");

            if (StringUtils.isNotEmpty(currentValue)
                    && Integer.parseInt(currentValue) > Integer.parseInt(valueToBeSet)) {
                int clicksQuantity = Integer.parseInt(currentValue) - Integer.parseInt(valueToBeSet);
                for (int i = 0; i < clicksQuantity; ++i) {
                    sleep(1);
                    removeElem.click();
                }

            } else if (StringUtils.isNotEmpty(currentValue)
                    && Integer.parseInt(currentValue) < Integer.parseInt(valueToBeSet)) {
                int clicksQuantity = Integer.parseInt(valueToBeSet) - Integer.parseInt(currentValue);
                for (int i = 0; i < clicksQuantity; ++i) {
                    sleep(5);
                    addElem.click();
                }
            } else {
                log.info(key + ": already contain the input value");
            }
        }
    }

    public void waitForLastStepElements(WebDriver driver) {
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Completá tus datos"));
        Assert.assertTrue(isWebElementDisplayed(driver, titleLoc), "title element was not visible on page");

        Assert.assertTrue(isWebElementDisplayed(driver, NAME_LOC),
                "Name element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, SURNAME_LOC),
                "Surname element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, BIRTHDAY_LOC),
                "Birthday element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, ID_NUMBER_LOC),
                "DNI element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, BIOLOGIC_GENDER_LOC),
                "Biologic gender element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, SELF_GENDER_LOC),
                "Gender element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, EMAIL_LOC),
                "Email element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, PHONE_CODE_LOC),
                "Phone code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, PHONE_NUMBER_LAST_LOC),
                "Phone number element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, STREET_LOC),
                "Street element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, HOUSE_NUMBER_LOC),
                "House Number element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, FLOOR_LOC),
                "Floor element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, APARTMENT_LOC),
                "Apartment element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, ZIP_CODE_LOC),
                "Zip code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, CITY_LOC),
                "City element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(driver, REGISTER_BUTTON_LOC),
                "Register button element was not visible on page");
    }

    public void setLastStepTextBoxes(WebDriver driver, List<List<String>> table) {
        DataTable data = createDataTable(table);
        if (data != null) {
            // AtomicInteger i = new AtomicInteger(1);
            data.cells()
                    .forEach(
                            value -> {
                                String KEY = "";
                                String VALUE = "";
                                try {
                                    List<String> rField = Collections.singletonList(value.get(0));
                                    List<String> rValue = Collections.singletonList(value.get(1));
                                    KEY = rField.get(0);
                                    VALUE = rValue.get(0);

                                } catch (NullPointerException e) {
                                    log.info(String.format("Path specified on table doesn't exist: %s", KEY));
                                    throw new SkipException(
                                            String.format("Path specified on table doesn't exist: %s", KEY));
                                }

                                if (StringUtils.containsIgnoreCase(KEY, "Nombre")) {
                                    jsSendKeys(driver, NAME_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Apellido")) {
                                    jsSendKeys(driver, SURNAME_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Fecha de nacimiento")) {
                                    jsSendKeys(driver, BIRTHDAY_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "DNI")) {
                                    jsSendKeys(driver, ID_NUMBER_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Sexo biológico")) {
                                    selectOptionDropdownByText(driver, BIOLOGIC_GENDER_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Género")) {
                                    selectOptionDropdownByText(driver, SELF_GENDER_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "E-mail")) {
                                    jsSendKeys(driver, EMAIL_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Cód. área")) {
                                    jsSendKeys(driver, PHONE_CODE_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Celular")) {
                                    jsSendKeys(driver, PHONE_NUMBER_LAST_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Calle")) {
                                    jsSendKeys(driver, STREET_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Número")) {
                                    jsSendKeys(driver, HOUSE_NUMBER_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Piso")) {
                                    jsSendKeys(driver, FLOOR_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Departamento")) {
                                    jsSendKeys(driver, APARTMENT_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Código Postal")) {
                                    jsSendKeys(driver, ZIP_CODE_LOC, VALUE);

                                } else if (StringUtils.containsIgnoreCase(KEY, "Ciudad")) {
                                    sleep(3);
                                    selectOptionDropdownByText(driver, CITY_LOC, VALUE);
                                }
                            });
        }

    }

}
