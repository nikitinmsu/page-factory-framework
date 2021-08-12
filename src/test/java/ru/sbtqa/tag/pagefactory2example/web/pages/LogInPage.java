package ru.sbtqa.tag.pagefactory2example.web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.api.junit.ApiSteps;
import ru.sbtqa.tag.pagefactory.WebPage;
import ru.sbtqa.tag.pagefactory.actions.PageActions;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.web.environment.WebEnvironment;

@PageEntry(title = "LogIn_ETPMarkets")
public class LogInPage extends WebPage {

    private static ApiSteps api;
    private static PageActions actions;

    @ElementTitle("login")
    @FindBy(xpath = "//*[@name = 'email']")
    public WebElement login;

    @ElementTitle("password")
    @FindBy(xpath = "//*[@name = 'password']")
    public WebElement password;

    @ElementTitle("signIn")
    @FindBy(css = ".sc-AykKE")
    public WebElement signInButton;


    public LogInPage() {
        api = ApiSteps.getInstance();
        actions = WebEnvironment.getPageActions();
    }

}
