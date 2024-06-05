package myProject.web.PageObjects;

import org.openqa.selenium.By;

public class OmayoPageObjects {

    public By txtFNameLoc = By.name("fname");
    public By txtFIdLoc = By.id("ta1");
    public By txtFXpathLoc = By.xpath("//h2[contains(text(), 'Text Area Field Two')]//following::textarea");

    public By multiselect1Loc = By.id("multiselect1");
    public By drop1Loc = By.id("drop1");

    public By table1xLoc = By.id("table1");
    public By table1Loc = By.cssSelector("table[id='table1'] td");
    public By table1PuneLoc = By.xpath("//table[@id='table1']//td[contains(., 'Pune')]");
    public By btnSameNameLoginLoc = By.xpath("//button[@name='samename' and contains(., 'Login')]");
    public By btnSameNameLoc = By.cssSelector("button[name='samename']");

    public By btnAlert1 = By.id("alert1");
    public By btnConfirm1 = By.id("confirm");
    public By btnPrompt1 = By.id("prompt");

    public By seleniumTutorialLinkLoc = By.id("selenium143");
    public By newPopUpLoc = By.linkText("Open a popup window");

    public By questionLoc = By.linkText("What is Selenium?");

    public By iframe1Loc = By.xpath("//iframe[@id='iframe1']");

    public By divQuestionFrame1Loc = By.xpath("(//div[@class='widget-content']//table[contains(., 'What is Selenium')])[1]");
    public By divQuestionFrame2Loc = By.cssSelector("div[id*='post-body']");

    public By iframe2Loc = By.xpath("//iframe[@id='iframe2']");

}
