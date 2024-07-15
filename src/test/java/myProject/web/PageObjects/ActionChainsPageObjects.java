package myProject.web.PageObjects;

import org.openqa.selenium.By;

public class ActionChainsPageObjects {

    public static By hoverDivParaLoc = By.id("hoverdivpara");
    public static By hoverLinkLoc = By.id("hoverlink");
    public static By menuWebTechLoc = By.xpath("//li[contains(., 'Web Tech')]");
    public static By htmlTutorialLoc = By.linkText("HTML Tutorial");

    public static By draggable2Loc = By.id("draggable2");
    public static By droppable1Loc = By.id("droppable1");

    public static By djangoCourseLoc = By.cssSelector("a[href*='views-in-django-python']");
    public static By footerLoc = By.id("gfg-footer");


}
