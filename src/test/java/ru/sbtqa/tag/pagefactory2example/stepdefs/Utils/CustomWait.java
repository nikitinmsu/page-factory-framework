package ru.sbtqa.tag.pagefactory2example.stepdefs.Utils;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.context.PageContext;
import ru.sbtqa.tag.pagefactory.environment.Environment;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.sbtqa.tag.pagefactory.exceptions.WaitException;
import ru.sbtqa.tag.pagefactory.web.utils.WebWait;

import java.util.Collections;
import java.util.List;

public class CustomWait extends WebWait {

    private final static Logger LOG = LoggerFactory.getLogger(CustomWait.class);

    /**
     * Время для ожидания выполнения условия по умолчанию
     */
    public static int DEFAULT_TIMEOUT = 30;
    public static int LOW_TIMEOUT = 5;
    /**
     * Время для ожидания перед повторной проверка условия
     */
    public static final double POLLING_TIME = 0.5;

    public static void waitForTextPresenceInPageSource(String text, int timeout) throws WaitException {
        presence(By.tagName("body"), "Element \"body\" did not appear after timeout");
        String xpath = String.format("//*[text()='%s']", text);
        presence(By.xpath(xpath), "", timeout);
    }

    /**
     * Ждет появления элемента на странице, проверяет кликабельный ли он и возвращает
     *
     * @param locator - локатор для элемента
     * @return - найденный элемент
     */
    public static WebElement waitForElementToPresenceAndBeClickable(By locator) {
        return waitForElementToPresenceAndBeClickableWithCustomTimeout(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Ждет появления элемента на странице, проверяет кликабельный ли он и возвращает, с кастомный таймаутом
     *
     * @param locator - локатор для элемента
     * @param timeout - время ожидания
     * @return - найденный элемент
     */
    public static WebElement waitForElementToPresenceAndBeClickableWithCustomTimeout(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            waitForMovingElement(locator);
            return webElement;
        } catch (Exception e) {
            throw new AssertionError("");
        }
    }

    public static WebElement waitForElementsWillPresenceAndGetFirstVisible(By locator, Integer timeout) {
        List<WebElement> elements = new WebDriverWait(Environment.getDriverService().getDriver(), timeout)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        WebElement firstVisible = elements.stream().filter(element -> element.isDisplayed()).findFirst().orElse(null);
        Assert.assertNotNull("", firstVisible);
        return firstVisible;
    }

    public static WebElement tryGetWebElement(By locator) {
        return getVisibleElement(locator, null);
    }

    public static WebElement tryGetWebElement(By locator, Integer timeout) {
        return getVisibleElement(locator, timeout);
    }

    public static WebElement getPresenceElement(By locator) {
        return getPresenceElement(locator, null);
    }

    /**
     *
     * @param locator локатор
     * @return возвращает коллекцию элементов
     */
    public static List<WebElement> tryGetWebElements(By locator) {
        return getElements(locator, null);
    }

    /**
     *
     * @param locator локатор
     * @param timeout таймаут
     * @return возвращает коллекцию элементов
     */
    public static List<WebElement> tryGetWebElements(By locator, Integer timeout) {
        return getElements(locator, timeout);
    }


    private static WebElement getVisibleElement(By locator, Integer timeout) {
        LOG.info("try get WebElement: {}", locator.toString());
        int time = timeout == null ? LOW_TIMEOUT : timeout;
        try {
            WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), time);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            LOG.warn("элемент с локатором {} по прошествии {} секунд не найден", locator.toString(), time);
            throw new NoSuchElementException("");
        }
    }

    public static WebElement getPresenceElement(By locator, Integer timeout) {
        LOG.info("try get WebElement: {}", locator.toString());
        int time = timeout == null ? LOW_TIMEOUT : timeout;
        try {
            WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), time);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            LOG.warn("элемент с локатором {} по прошествии {} секунд не найден", locator.toString(), time);
            throw new NoSuchElementException("");
        }
    }

    private static List<WebElement> getElements(By locator, Integer timeout) {
        LOG.info("try get WebElements: {}", locator.toString());
        int time = timeout == null ? LOW_TIMEOUT : timeout;
        try {
            WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), time);
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            LOG.warn("элементы с локатором {} не найдены, возвращаем пустую коллекцию", locator.toString());
            return Collections.emptyList();
        }
    }

    public static void waitForClickability(String elementTitle, Integer timeout) throws PageException {
        int time = timeout == null ? LOW_TIMEOUT : timeout;
        String error = "";
        try {
            WebElement webElement = Environment.getFindUtils().getElementByTitle(PageContext.getCurrentPage(), elementTitle);
            WebWait.clickable(webElement, error, time);
        } catch (StaleElementReferenceException e) {
            WebElement element = Environment.getFindUtils().getElementByTitle(PageContext.getCurrentPage(), elementTitle);
            if (element.isEnabled()) {
                WebWait.clickable(element, error, time);
            }
        }
    }

    /**
     * Ждет пока анимация элемента завершится
     *
     * @param locator -  локатор для элемента
     */
    public static void waitForMovingElement(By locator) {
        WebElement webElementNew;
        Point start = new Point(0, 0);
        boolean condition = true;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        do {
            try {
                condition = !(Environment.getDriverService().getDriver().findElement(locator).getLocation().equals(start));
                webElementNew = Environment
                        .getDriverService()
                        .getDriver()
                        .findElement(locator);
                start = webElementNew.getLocation();
                waitPollingTime(POLLING_TIME);
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                waitPollingTime(POLLING_TIME);
            }
        } while (condition && stopWatch.getTime() < DEFAULT_TIMEOUT * 1000);
    }

    /**
     * Ждет пока анимация элемента завершится
     *
     * @param webElement -  веб-элемент
     */
    public static void waitForMovingElementByElement(WebElement webElement) {
        WebElement webElementNew;
        Point start = new Point(0, 0);
        int i = 1;
        while (!webElement.getLocation().equals(start)) {
            waitPollingTime(1);
            webElementNew = webElement;
            start = webElementNew.getLocation();
            i++;
        }
    }

    /**
     * Таймер ожидания
     *
     * @param pollingTime - время в секундах
     */
    public static void waitPollingTime(double pollingTime) {
        try {
            Thread.sleep((int) (pollingTime * 1000));
        } catch (InterruptedException interrupt) {
            System.out.println("проблема со sleep потока" + interrupt);
        }
    }

    public static void checkThatElementIsNotPresent(WebElement element, String elementTitle) {
        try {
            if (element.isDisplayed()) {
                throw new AssertionError("");
            }
        } catch (NoSuchElementException e) {
            //элемента нет или он пропал
        }
    }

    public static boolean checkThatButtonIsNotAvailable(WebElement button) {
        WebElement element;
        boolean state = false;
        try {
            WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), DEFAULT_TIMEOUT);
            element = wait.until(ExpectedConditions.visibilityOf(button));
            if (element != null) {
                state = element.isEnabled();
            }
        } catch (Exception ignored) {
        }
        return state;
    }

    public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    public static WebElement waitForElementWillPresence(By locator, Integer timeout) {
        WebDriverWait customWait = new WebDriverWait(Environment.getDriverService().getDriver(), timeout);
        return customWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean isElementWillAppearDuringTimeout(By locator, Integer timeout) {
        try {
            getVisibleElement(locator, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}