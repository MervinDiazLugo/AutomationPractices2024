package myProject.web.PageObjects;

import org.openqa.selenium.By;

public class OrangeHRMPageObjects {

    public By userNameLoc = By.name("username");
    public By passwordLoc =  By.name("password");
    public By loginBtnLoc = By.xpath("//button[@type='submit']");
    public By userBulletLoc = By.cssSelector("img[alt='profile picture']");

    public By adminUsersTableListLoc = By.cssSelector("div[class='oxd-table-card']");
    public By adminModuleLoc = By.cssSelector("a[href='/web/index.php/admin/viewAdminModule']");


}
