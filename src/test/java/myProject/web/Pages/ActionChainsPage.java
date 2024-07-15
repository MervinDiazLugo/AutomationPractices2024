package myProject.web.Pages;

import config.web.WebDriverHelper;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;


import static myProject.web.PageObjects.ActionChainsPageObjects.*;


@Log
public class ActionChainsPage extends WebDriverHelper {
    public void dragAndDropElement(WebDriver driver){
        //build actions
        Action seriesOfActions = dragAndDropToElement(driver,
                draggable2Loc, droppable1Loc);
        seriesOfActions.perform();
    }

    public void moveAndClick(WebDriver driver){

        //build actions
        Action seriesOfActions = moveToElement(driver, menuWebTechLoc);
        seriesOfActions.perform();

        Action seriesOfActions2 = moveToElementAndClick(driver, htmlTutorialLoc);
        seriesOfActions2.perform();

    }

}
