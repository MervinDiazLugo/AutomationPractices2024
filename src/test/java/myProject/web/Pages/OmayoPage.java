package myProject.web.Pages;


import config.web.WebDriverHelper;
import lombok.extern.java.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

}
