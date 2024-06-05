package myProject.web.Pages;


import config.web.WebDriverHelper;
import myProject.web.PageObjects.OrangeHRMPageObjects;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;

import java.util.List;

public class OrangeHRMPage extends WebDriverHelper {
    OrangeHRMPageObjects orangeHRMPageObjects = new OrangeHRMPageObjects();
    public void login(WebDriver driver, String user, String pass){
        fillUsername(driver, user);
        fillPassword(driver, pass);
        clickLoginButton(driver);
    }

    public void loginAdminUser(WebDriver driver){
        JSONObject adminUser = adminUserCredentials();

        String user = adminUser.get("userName").toString();
        String pass = adminUser.get("password").toString();

        fillUsername(driver, user);
        fillPassword(driver, pass);
        clickLoginButton(driver);
    }

    public void fillUsername(WebDriver driver, String user){
        WebElement userNameElm = getElement(driver, orangeHRMPageObjects.userNameLoc);

        if(StringUtils.isEmpty(user)){
            throw new SkipException("Variable user is empty");
        }

        if(userNameElm!=null){
            userNameElm.sendKeys(user);
        }else{
            throw new SkipException("Username text box is not present");
        }
    }

    public void fillPassword(WebDriver driver, String password){
        WebElement userPasswordElm = getElement(driver, orangeHRMPageObjects.passwordLoc);

        if(StringUtils.isEmpty(password)){
            throw new SkipException("Variable password is empty");
        }

        if(userPasswordElm!=null){
            userPasswordElm.sendKeys(password);
        }else{
            throw new SkipException("Password text box is not present");
        }
    }

    public void clickLoginButton(WebDriver driver){
        WebElement loginButtonElm = getElement(driver, orangeHRMPageObjects.loginBtnLoc);

        if(loginButtonElm!=null){
            loginButtonElm.click();
        }else{
            throw new SkipException("Login Button is not present");
        }
    }

    public WebElement getUserBulletElem(WebDriver driver){
        return getElement(driver, orangeHRMPageObjects.userBulletLoc);
    }

    public void goToSystemAdminUsers(WebDriver driver){
        WebElement adminMenuElm = getElement(driver, orangeHRMPageObjects.adminModuleLoc);

        if(adminMenuElm!=null){
            adminMenuElm.click();
        }else{
            throw new SkipException("Admin item at main menu is not present");
        }
    }

    public void getSystemUserList(WebDriver driver, String userName){
        List<WebElement> systemUserElm = getElements(driver, orangeHRMPageObjects.adminUsersTableListLoc);
        boolean isPresent = false;

        if(!systemUserElm.isEmpty()){
            for(WebElement elem : systemUserElm){
                String[] currentUsernameData = StringUtils.split(elem.getText(), "\n");
                String currentUsername = "";
                if(currentUsernameData.length>0){
                    currentUsername = currentUsernameData[0];
                }else{
                    throw new SkipException("currentUsernameData is empty");
                }
                if(StringUtils.isNotEmpty(currentUsername) &&
                        StringUtils.equalsIgnoreCase(currentUsername, userName)){
                    isPresent = true;
                    break;
                }
            }
        }else{
            throw new SkipException("Admin item at main menu is not present");
        }

        Assert.assertTrue(isPresent, "The username was not present");
    }

}
