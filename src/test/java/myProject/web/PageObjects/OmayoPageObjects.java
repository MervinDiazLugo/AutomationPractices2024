package myProject.web.PageObjects;

import org.openqa.selenium.By;

public class OmayoPageObjects {

    public static By txtFNameLoc = By.name("fname");
    public static By txtFIdLoc = By.id("ta1");
    public static By txtFXpathLoc = By.xpath("//h2[contains(text(), 'Text Area Field Two')]//following::textarea");

    public static By multiselect1Loc = By.id("multiselect1");
    public static By drop1Loc = By.id("drop1");

    public static By table1xLoc = By.id("table1");
    public static By table1Loc = By.cssSelector("table[id='table1'] td");
    public static By table1PuneLoc = By.xpath("//table[@id='table1']//td[contains(., 'Pune')]");
    public static By btnSameNameLoginLoc = By.xpath("//button[@name='samename' and contains(., 'Login')]");
    public static By btnSameNameLoc = By.cssSelector("button[name='samename']");

    public static By btnAlert1 = By.id("alert1");
    public static By btnConfirm1 = By.id("confirm");
    public static By btnPrompt1 = By.id("prompt");

    public static By seleniumTutorialLinkLoc = By.id("selenium143");
    public static By newPopUpLoc = By.linkText("Open a popup window");

    public static By questionLoc = By.linkText("What is Selenium?");

    public static By iframe1Loc = By.xpath("//iframe[@id='iframe1']");

    public static By divQuestionFrame1Loc = By.xpath("(//div[@class='widget-content']//table[contains(., 'What is Selenium')])[1]");
    public static By divQuestionFrame2Loc = By.cssSelector("div[id*='post-body']");

    public static By iframe2Loc = By.xpath("//iframe[@id='iframe2']");

}
