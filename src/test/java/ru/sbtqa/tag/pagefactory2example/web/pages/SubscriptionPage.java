package ru.sbtqa.tag.pagefactory2example.web.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sbtqa.tag.pagefactory.WebPage;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.environment.Environment;
import ru.sbtqa.tag.pagefactory2example.stepdefs.Utils.CustomWait;

@PageEntry(title = "ETPSubscription")
public class SubscriptionPage extends WebPage {

    WebDriver driver = Environment.getDriverService().getDriver();

    @ElementTitle("Add Exchange")
    @FindBy(xpath = "//*[contains(text(),'Add Exchange')]")
    public WebElement addExchange;

    @ElementTitle("Delete selected subscriptions")
    @FindBy(xpath = "//*[@data-tip='Delete selected subscriptions']")
    public WebElement DeleteSelectedSubscriptions;

    //region Add new Exchange popup window
    @ElementTitle("protocol type")
    @FindBy(css = ".sc-LzLtL")
    public WebElement protocolType;

    @ElementTitle("plus number of session")
    @FindBy(css = ".sc-fzXfQT  .sc-LzLwo")
    public WebElement plusNumberOfSession;

    @ElementTitle("minus number of session")
    @FindBy(css = ".sc-fzXfQT .sc-LzLwp")
    public WebElement minusNumberOfSession;

    @ElementTitle("subscription add button")
    @FindBy(css = ".sc-AykKC > .sc-AykKE")
    public WebElement subscriptionAddButton;

    //endregion

    //Actions
    @ActionTitle("select Protocol type")
    public void selectRole(String text) {
        try {
            this.selectValueFromDropDown(text, By.cssSelector(".sc-LzLtL"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
    }

    @ActionTitle("checks text in following item in the list")
    public void selectRole(String value, String number) {
        By locator = By.xpath(String.format("//*[@class = 'sc-LzLvb hShBUW'][%s]//*[contains(text(),'%s')]", number, value));
        try {
            boolean isElementAppears = CustomWait.isElementWillAppearDuringTimeout(locator, 10);
            Assert.assertTrue(isElementAppears);
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
    }

    @ActionTitle("press on following number of checkbox in subscription list")
    public void selectCheckbox(String number) {
        By locator;

        if (number.equals("All")) {
            locator = By.xpath("(//*/input[@type = \'checkbox\' and @class = \'sc-LzLrl idPkBn\'])[1]/../..//*[@data-icon = 'check']");
        } else {
            Integer numberWithoutTopCheckbox = Integer.parseInt(number) + 1;
            locator = By.xpath(String.format("(//*/input[@type = \'checkbox\' and @class = \'sc-LzLrl idPkBn\'])[%s]/../..//*[@data-icon = 'check']", numberWithoutTopCheckbox));
        }

        try {

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();

        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
    }

    @ActionTitle("check count of elements in subscription list")
    public void selectCountOfElements(String expectedCountString) {

        By locator = By.xpath("//*[contains(@class, 'sc-LzLvb')]");
        int actualCount = driver.findElements(locator).size();
        int expectedCount = Integer.parseInt(expectedCountString);

        Assert.assertEquals(String.format("Actual Count - %s, expected count - %s", actualCount, expectedCount), actualCount, expectedCount);
    }


    private void selectValueFromDropDown(String value, By locator) {
        if (!value.trim().isEmpty()) {
            CustomWait.tryGetWebElement(locator).click();
            CustomWait.tryGetWebElement(By.xpath(String.format("//*[text()='%s']", value))).click();
            Assert.assertEquals(value, CustomWait.tryGetWebElement(locator).getAttribute("value").trim());
        }
    }

    private void pressCheckBox(By locator) {
        String checkBoxState = CustomWait.tryGetWebElement(locator).getAttribute("value").trim();
        CustomWait.tryGetWebElement(locator).click();
        Assert.assertNotEquals(checkBoxState, CustomWait.tryGetWebElement(locator).getAttribute("value").trim());

    }

}
