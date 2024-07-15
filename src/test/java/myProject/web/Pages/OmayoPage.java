package myProject.web.Pages;


import config.web.WebDriverHelper;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;

import java.util.List;
import java.util.Optional;

import static myProject.web.PageObjects.OmayoPageObjects.*;
@Log
public class OmayoPage extends WebDriverHelper {

    public void switchAndPrintTextMenu(WebDriver driver){
        WebElement frame1Elem = getElement(driver, iframe1Loc);
        if(frame1Elem!=null){
            driver.switchTo().frame(frame1Elem);
            WebElement divQuestionFrame1Elem = getElement(driver, divQuestionFrame1Loc);
            log.info(divQuestionFrame1Elem.getText());
        }
    }

    public void switchAndPrintMenuFrame2(WebDriver driver){
        WebElement frame2Elem = getElement(driver, iframe2Loc);
        if(frame2Elem!=null){
            driver.switchTo().frame(frame2Elem);
            WebElement divQuestionFrame2Elem = getElement(driver, divQuestionFrame2Loc);
            if(divQuestionFrame2Elem!=null){
                log.info(divQuestionFrame2Elem.getText());
            }

        }
    }

    public void clickAlertAccept(WebDriver driver){
        WebElement alert1Elem = getElement(driver, btnAlert1);
        if(alert1Elem!=null){
            alert1Elem.click();
            Alert alert1 = isAlertPresent(driver);
            if(alert1!=null){
                alert1.accept();
            }
        }
    }

    public void selenium1TextBoxTest1(WebDriver driver, String text) {
        WebElement txtFNameElem = getElement(driver, txtFNameLoc);

        if(txtFNameElem!=null){
            txtFNameElem.clear();
            txtFNameElem.sendKeys(text);
        }else{
            throw new SkipException("Skipping the test case");
        }

    }

    public void selenium1TextBoxTest(WebDriver driver) {
        WebElement txtFIdElem = getElement(driver, txtFIdLoc);
        WebElement txtFXpathElem = getElement(driver, txtFXpathLoc);

        if(txtFIdElem!=null && txtFXpathElem!=null){
            txtFIdElem.clear();
            txtFIdElem.sendKeys("Hola mundo 2");

            txtFXpathElem.clear();
            txtFXpathElem.sendKeys("Hola mundo 3");
        }else{
            throw new SkipException("Skipping the test case");
        }

    }

    public void seleniumDropdownTest1(WebDriver driver, String text) {
        //WEB ELEMENT
        WebElement dropElm = getElement(driver, drop1Loc);
        Select dropdown = dropElm!=null ? new Select(dropElm) : null;

        if(dropdown!=null){
            //SELECT by visible text
            dropdown.selectByVisibleText(text);

            //SELECT by value (ver en el DOM ej jkl = doc 3)
            //dropdown.selectByValue("jkl");
            //SELECT by index
            //dropdown.selectByIndex(0);

        }else{
            throw new SkipException("Skipping the test case");
        }
    }

    public void seleniumMultiSelectTest1(WebDriver driver) {
        //WEB ELEMENT
        WebElement multiselectELem = getElement(driver, multiselect1Loc);
        Select multiselect = multiselectELem!=null ? new Select(multiselectELem) : null;

        if(multiselect!=null){
            //SELECT by visible text
            multiselect.selectByValue("audix");
            multiselect.selectByValue("volvox");

        }else{
            throw new SkipException("Skipping the test case");
        }
    }

    public void seleniumDropdownFilterTest1(WebDriver driver) {
        //WEB ELEMENT
        WebElement dropElm = getElement(driver, drop1Loc);
        Select dropdown = dropElm!=null ? new Select(dropElm) : null;

        if(dropdown!=null){
            List<WebElement> dropOpt = dropdown.getOptions();
            Optional<WebElement> dropMatchingOption = dropOpt.stream()
                    .filter(option -> option.getText().equals("doc 4"))
                    .findFirst();
            dropMatchingOption.ifPresent(elm -> elm.click());

        }else{
            throw new SkipException("Skipping the test case");
        }
    }

    public WebElement findInWebElementList(WebDriver driver, String text){
        //WEB ELEMENT
        List<WebElement> btnElem = getElements(driver, btnSameNameLoc);
        WebElement resultElement = null;
        //Encontrar un texto dentro de un WebElement List
        for(WebElement elem : btnElem){
            if(elem.getText().equals(text)){
                System.out.println(elem.getText());
                resultElement = elem;
                break;
            }
        }
        return resultElement;
    }

    public void selenium5AlertPrompt1(WebDriver driver){
        getElement(driver, btnAlert1).click();
        Alert btnAlert1 = isAlertPresent(driver);

        if(btnAlert1 != null){
            System.out.println(btnAlert1.getText());
            btnAlert1.accept();
        }

        getElement(driver, btnConfirm1).click();
        Alert btnConfirm1 = isAlertPresent(driver);
        if(btnConfirm1 != null){
            System.out.println(btnConfirm1.getText());
            btnConfirm1.dismiss();
        }

        getElement(driver, btnPrompt1).click();
        Alert btnPrompt1 = isAlertPresent(driver);
        if(btnPrompt1 != null){
            System.out.println(btnPrompt1.getText());
            btnPrompt1.sendKeys("Hola Mundo");
            btnPrompt1.accept();
        }

    }

    public void openLinkPage(WebDriver driver, String name){
        WebElement openLink = null;
        if(StringUtils.equalsIgnoreCase(name, "selenium143")){
            openLink = getElement(driver, seleniumTutorialLinkLoc);
            if(openLink != null){
                openLink.click();
            }else{
                throw new SkipException("Skipping the test case");
            }
        }else{
            throw new SkipException("Skipping the test case");
        }


    }

    public void iSwitchedThroughTheFrames(WebDriver driver){
        switchAndPrintTextMenu(driver);
        driver.switchTo().parentFrame();
        clickAlertAccept(driver);
        switchAndPrintMenuFrame2( driver);
        driver.switchTo().defaultContent();
        clickAlertAccept(driver);
    }

    public void iModifiedTableAttributes(WebDriver driver) {
        WebElement table1Elem = getElement(driver, table1xLoc);
        if(table1Elem != null){
            scrollToElement(driver, table1Elem);
            setAttribute(driver, table1xLoc, "border", "50");
            setAttribute(driver, table1Elem, "border", "100");
        }else{
            throw new SkipException("Skipping the test case");
        }

    }



}
