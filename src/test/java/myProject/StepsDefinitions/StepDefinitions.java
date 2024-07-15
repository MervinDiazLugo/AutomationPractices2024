package myProject.StepsDefinitions;

import config.web.WebDriverHelper;
import config.web.WebDriverProperties;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

import lombok.extern.java.Log;
import myProject.web.Pages.ActionChainsPage;
import myProject.web.Pages.EmergenciasPage;
import myProject.web.Pages.OmayoPage;
import myProject.web.Pages.OrangeHRMPage;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;


@Log
public class StepDefinitions extends WebDriverHelper {

    WebDriver driver = null;
    Scenario scenario = null;
    public StepDefinitions() {
        driver = Hooks.driver;
        scenario = Hooks.scenario;
        windowsHandle.put("main", driver.getWindowHandle());
    }

    OrangeHRMPage orangeHRMPage = new OrangeHRMPage();

    OmayoPage omayoPage = new OmayoPage();
    EmergenciasPage emergenciasPage = new EmergenciasPage();
    ActionChainsPage actionChainsPage = new ActionChainsPage();
    WebDriverProperties wdp = new WebDriverProperties();

    @Given("^an example scenario$")
    public void anExampleScenario() {
        log.info("Hola Mundo!");
        driver.getCurrentUrl();
        driver.getTitle();
    }

    @When("^all step definitions are implemented$")
    public void allStepDefinitionsAreImplemented() {
    }

    @Then("^the scenario passes$")
    public void theScenarioPasses() {
    }

    @Given("^The User fill username text box$")
    public void theUserFillUsernameTextBox() {
        orangeHRMPage.fillUsername(driver, "Admin");
    }

    @And("^The User fill password text box$")
    public void theUserFillPasswordTextBox() {
        orangeHRMPage.fillPassword(driver, "admin123");
    }

    @When("^The User clicks Login button$")
    public void theUserClicksLoginButton() {
        orangeHRMPage.clickLoginButton(driver);
    }


    @When("^the user is Logged in$")
    public void theUserIsLoggedIn() {
        orangeHRMPage.login(driver, wdp.getMainUserName(), wdp.getMainPassword());
    }

    @Then("^Verify the user is logged in$")
    public void verifyTheUserIsLoggedIn() {
        WebElement userBulletElm = orangeHRMPage.getUserBulletElem(driver);
        Assert.assertNotNull(userBulletElm, "El login fue incorrecto");
    }

    @Then("^The user go to System user list$")
    public void theUserGoToSystemUserList() {
        orangeHRMPage.goToSystemAdminUsers(driver);
    }

    @When("^Verify (.*?) user is present in the list$")
    public void verifyAdminUserIsPresentInTheList(String userName) {
        orangeHRMPage.getSystemUserList(driver, userName);
    }

    @When("^the Admin user is Logged in$")
    public void theAdminUserIsLoggedIn() {
        orangeHRMPage.loginAdminUser(driver);
    }


    @Given("^I am waiting for the first step pages to load$")
    public void iAmWaitingForTheFirstStepPagesToLoad() {
        emergenciasPage.waitForFirstStepElements(driver);
    }

    @Then("^I am filling the following text boxes:$")
    public void iAmFillingTheFollowingTextBoxes(List<List<String>> table) {
        emergenciasPage.setFirstStepTextBoxes(driver, table);
    }

    @And("^click on (.*?) button$")
    public void clickOnCotizaButton(String tag) {
        emergenciasPage.clickOnButtons(driver, tag);
    }

    @And("^I wait for second step elements are loaded$")
    public void iWaitForSecondStepElementsAreLoaded() {
        emergenciasPage.waitForSecondStepElements(driver);
    }

    @Then("I fill following text boxes:")
    public void iFillFollowingTextBoxes(List<List<String>> table) {
        emergenciasPage.setFirstStepTextBoxes(driver, table);
    }

    @And("^I am waiting for the last step elements to load$")
    public void iWaitForLastStepElementsAreLoaded() {
        emergenciasPage.waitForLastStepElements(driver);
    }

    @Then("^I am filling out the registration form with values:$")
    public void iFillRegistrationFormWithValues(List<List<String>> table) {
        emergenciasPage.setLastStepTextBoxes(driver, table);
    }

    @And("^I saved a screenshot$")
    public void iSavedAScreenshot() throws IOException {
        takeScreenShot(driver, scenario);
    }

    @Then("^I filled the sample textbox with (.*?)$")
    public void iFilledTheSampleTextBoxWith(String text) {
        omayoPage.selenium1TextBoxTest1(driver, text);
    }

    @Then("^I filled the other sample textbox$")
    public void iFilledTheOtherSampleTextbox() {
        omayoPage.selenium1TextBoxTest(driver);
    }

    @Then("^I selected the sample dropdown with text (.*?)$")
    public void iSelectedTheSampleDropdownWithText(String text) {
        omayoPage.seleniumDropdownTest1(driver, text);
    }

    @Then("^I selected the other sample multiselect$")
    public void iSelectedTheOtherSampleMultiselect() {
        omayoPage.seleniumMultiSelectTest1(driver);
    }

    @Then("^I selected the dropdown using filters$")
    public void iSelectedTheDropdownUsingFilters() {
        omayoPage.seleniumDropdownFilterTest1(driver);
    }

    @Then("^I searching for a element with text (.*?)$")
    public void iSearchingForAElementWithTextDoc(String text) {
        WebElement table1Elem = omayoPage.findInWebElementList(driver, text);
        Assert.assertNotNull(table1Elem, "Element was not present");
    }

    @Then("^I closed the system alert$")
    public void iClosedTheSystemAlert() {
        omayoPage.selenium5AlertPrompt1(driver);
    }

    @Then("^I switched to the window called (.*?)$")
    public void iSwitchedToTheWindowCalledSelenium(String name) {
        getWindowsHandle(driver, name);
    }


    @Then("^I clicked on (.*?) link$")
    public void iClickedOnSeleniumLink(String name) {
        omayoPage.openLinkPage(driver, name);
    }

    @Then("^I switched through the frames$")
    public void iSwitchedThroughTheFrames() {
        omayoPage.iSwitchedThroughTheFrames(driver);
    }

    @Then("^I modified table attributes$")
    public void iModifiedTableAttributes() throws IOException {
        omayoPage.iModifiedTableAttributes(driver);
        takeScreenShot(driver, scenario);
    }

    @Then("^I navigated to (.*?)$")
    public void iNavigatedTo(String url) {
        driver.get(url);
    }

    @Then("^I drag and drop two elements$")
    public void iDragAndDropTwoElements() {
        actionChainsPage.dragAndDropElement(driver);
    }

    @Then("^I moved and click element$")
    public void iMovedAndClickElement() {
        actionChainsPage.moveAndClick(driver);
    }
}
