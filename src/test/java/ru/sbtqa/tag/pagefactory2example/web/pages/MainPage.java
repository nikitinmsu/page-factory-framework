package ru.sbtqa.tag.pagefactory2example.web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.api.junit.ApiSteps;
import ru.sbtqa.tag.pagefactory.WebPage;
import ru.sbtqa.tag.pagefactory.actions.PageActions;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.web.environment.WebEnvironment;

@PageEntry(title = "Main_ETPMarkets")
public class MainPage extends WebPage {

    private static ApiSteps api;
    private static PageActions actions;


    @ElementTitle("sign-in")
    @FindBy(xpath = "//*[@href = '/auth/sign-in']")
    public WebElement signIn;


    public MainPage() {
        api = ApiSteps.getInstance();
        actions = WebEnvironment.getPageActions();
    }

}
