package ru.sbtqa.tag.pagefactory2example.stepdefs.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import ru.sbtqa.tag.pagefactory.environment.Environment;

public class JavaScriptUtils {

    public static void javaScriptExecute(WebElement element, String script) {
        JavascriptExecutor js = Environment.getDriverService().getDriver();
        js.executeScript(script, element);
    }

    public static Object javaScriptExecute(String script) {
        JavascriptExecutor js = Environment.getDriverService().getDriver();
        return js.executeScript(script);
    }

    public static String javaScriptExecuteAndReturnValue(WebElement element, String script) {
        JavascriptExecutor js = Environment.getDriverService().getDriver();
        return js.executeScript(script, element).toString();
    }

    /**
     * Ищет веб-элемент с помощью xpath и скроллит до элемента с помощью JS
     * возвращает найденный элемент
     *
     * @param locator -   путь до элемента
     * @return -   найденный элемент
     */
    public static WebElement scrollIntoViewAndReturnElement(By locator) {
        WebElement webElement = CustomWait.getPresenceElement(locator); //Environment.getDriverService().getDriver().findElement(By.xpath(xpath));
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("arguments[0].scrollIntoView(false);", webElement);
        return webElement;
    }

    public static WebElement scrollIntoScreenCenterAndReturnElement(By locator) {
        WebElement webElement = CustomWait.getPresenceElement(locator); //Environment.getDriverService().getDriver().findElement(By.xpath(xpath));
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", webElement);
        return webElement;
    }

    /**
     * Скроллит до  элемента с помощь js
     *
     * @param webElement -   веб-элемент
     */
    public static void scrollIntoViewElement(WebElement webElement) {
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("arguments[0].scrollIntoView(false);", webElement);
    }

    /**
     * Скроллит до  элемента и ставит его по центру экрана с помощью js
     *
     * @param webElement -   веб-элемент
     */
    public static void scrollIntoScreenCenterElement(WebElement webElement) {
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", webElement);
    }

    /**
     * Ищет веб-элемент в родительском элементе с помощью xpath и скроллит до элемента с помощью JS
     * возвращает найденный элемент
     *
     * @param parentWebElement -   родительский элемент
     * @param xpath            -   путь до элемента
     * @return -   найденный элемент
     */
    public static WebElement scrollIntoViewAndReturnElementInParent(WebElement parentWebElement, String xpath) {
        WebElement webElement = parentWebElement.findElement(By.xpath(xpath));
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("arguments[0].scrollIntoView(false);", webElement);
        return webElement;
    }

    public static void scrollPageToBottom() {
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public static void openLinkInNewTab(String link) {
        ((JavascriptExecutor) Environment.getDriverService().getDriver()).executeScript("window.open('" + link + "');");
    }
}
