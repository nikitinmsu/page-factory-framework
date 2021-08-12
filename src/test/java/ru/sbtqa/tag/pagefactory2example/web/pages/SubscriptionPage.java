package ru.sbtqa.tag.pagefactory2example.web.pages;

import com.sun.org.apache.xpath.internal.XPath;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.api.junit.ApiSteps;
import ru.sbtqa.tag.pagefactory.WebPage;
import ru.sbtqa.tag.pagefactory.actions.PageActions;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory2example.stepdefs.Utils.CustomWait;

@PageEntry(title = "ETPSubscription")
public class SubscriptionPage extends WebPage {

    private static ApiSteps api;
    private static PageActions actions;

    @ElementTitle("Add Exchange")
    @FindBy(xpath = "//*[contains(text(),'Add Exchange')]")
    public WebElement addExchange;

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

        if(number.equals("Top")){
            locator = By.xpath("//*/input[@type = \'checkbox\' and @class = \'sc-LzLrl idPkBn\'][1]");
        }else{
            Integer numberWithoutTopCheckbox = Integer.parseInt(number) + 1;
            locator = By.xpath(String.format("//*/input[@type = \'checkbox\' and @class = \'sc-LzLrl idPkBn\'][%s]", numberWithoutTopCheckbox));
        }

        try {
            this.pressCheckBox(locator);
        } catch (org.openqa.selenium.NoSuchElementException e) {
        }
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
