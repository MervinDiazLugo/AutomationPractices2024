package myProject.web.Pages;


import config.web.WebDriverHelper;
import myProject.web.PageObjects.OmayoPageObjects;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OmayoPage extends WebDriverHelper {

    OmayoPageObjects omayoPageObjects = new OmayoPageObjects();

    public void switchAndPrintTextMenu(WebDriver driver){
        WebElement frame1Elem = getElement(driver, omayoPageObjects.iframe1Loc);
        if(frame1Elem!=null){
            driver.switchTo().frame(frame1Elem);
            WebElement divQuestionFrame1Elem = getElement(driver, omayoPageObjects.divQuestionFrame1Loc);
            System.out.println(divQuestionFrame1Elem.getText());
        }
    }

    public void switchAndPrintMenuFrame2(WebDriver driver){
        WebElement frame2Elem = getElement(driver, omayoPageObjects.iframe2Loc);
        if(frame2Elem!=null){
            driver.switchTo().frame(frame2Elem);
            WebElement divQuestionFrame2Elem = getElement(driver, omayoPageObjects.divQuestionFrame2Loc);
            if(divQuestionFrame2Elem!=null){
                System.out.println(divQuestionFrame2Elem.getText());
            }

        }
    }

    public void clickAlertAccept(WebDriver driver){
        WebElement alert1Elem = getElement(driver, omayoPageObjects.btnAlert1);
        if(alert1Elem!=null){
            alert1Elem.click();
            Alert alert1 = isAlertPresent(driver);
            if(alert1!=null){
                alert1.accept();
            }
        }
    }

}
