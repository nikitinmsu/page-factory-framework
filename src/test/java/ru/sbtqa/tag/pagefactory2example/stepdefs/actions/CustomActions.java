package ru.sbtqa.tag.pagefactory2example.stepdefs.actions;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sbtqa.tag.pagefactory.context.PageContext;
import ru.sbtqa.tag.pagefactory.environment.Environment;
import ru.sbtqa.tag.pagefactory.web.actions.WebPageActions;
import ru.sbtqa.tag.pagefactory.web.utils.WebWait;
import ru.sbtqa.tag.pagefactory2example.stepdefs.Utils.CustomWait;

import static ru.sbtqa.tag.pagefactory2example.stepdefs.Utils.CustomWait.waitForLoad;


public class CustomActions extends WebPageActions {

    @Override
    public void click(Object element) {
        String elementTitle = Environment.getReflection().getElementTitle(PageContext.getCurrentPage(), element);
        WebWait.clickable((WebElement) element,"" );
        super.click(element);
        waitForLoad(Environment.getDriverService().getDriver());
    }

    @Override
    public void fill(Object element, String text) {
        WebElement webElement = (WebElement) element;
        clearField(webElement);
        webElement.sendKeys(Keys.HOME + text);
    }

    /**
     * Находит и кликает на элемент
     *
     * @param locator - локатор для элемента
     */
    public static void click(By locator) {
        CustomWait.waitForElementToPresenceAndBeClickable(locator).click();
        waitForLoad(Environment.getDriverService().getDriver());
    }

    /**
     * Считает количество элементов на странице с переданным локатором
     *
     * @param locator - локатор для элемента
     * @return - количество элементов на странице
     */
    public static int countElementsByLocator(By locator) {
        WebDriverWait wait = new WebDriverWait(Environment.getDriverService().getDriver(), CustomWait.DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)).size();
    }

    /**
     * Заполняет поле посимвольно
     * При ...sendKeys(вся строка) иногда символы меняются местами
     *
     * @param input -   веь элемент для ввода
     * @param text  -   текст, который надо ввести
     */
    public static void fillInputByCharacter(WebElement input, String text) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String normalizedText = text.toLowerCase().replaceAll("\\s", "");
        boolean isPhoneField = false;
        try {
            //для ввода номера в формах КАСКО новых
            input.findElement(By.xpath(".//following-sibling::*[text()='Номер телефона']"));
            isPhoneField = true;
        } catch (NoSuchElementException e) {
            //для ввода номера в форме КАСКО старых
            isPhoneField = input.getAttribute("placeholder").equals("Телефон");
        }
        do {
            clearField(input);
            for (char character : text.toCharArray()) {
                input.sendKeys(String.valueOf(character));
            }
            if (!isPhoneField) {
                if (input.getAttribute("placeholder").equals(text)
                        || input.getAttribute("value").toLowerCase().replaceAll("\\s", "").equals(normalizedText)
                        || input.getText().equals(text)) {
                    break;
                }
            } else {
                if (normalize(input.getAttribute("value").replaceAll("\\D", "")).equals("79".concat(text))) {
                    break;
                }
            }
        } while (stopWatch.getTime() < 10 * 1000);
        stopWatch.stop();
    }

    /**
     * @param pause - пауза между вводом симвалов, необходимая для подгрузки данных с бэка
     */
    public static void fillInputByCharacter(WebElement input, String text, double pause) {
        clearField(input);
        for (char character : text.toCharArray()) {
            input.sendKeys(String.valueOf(character));
            //Waits.waitPollingTime(pause);
        }
    }

    public static void clickInField(WebElement input) {
        try {
            input.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException ignored) {
        }
    }

    public static void clearField(WebElement input) {
        input.clear();
        clickInField(input);
        input.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
    }

    public static String normalize(String phones) {
        if (phones != null && !phones.isEmpty()) {
            phones = StringUtils.remove(phones, "+");
            phones = StringUtils.remove(phones, ")");
            phones = StringUtils.remove(phones, "-");
            phones = StringUtils.remove(phones, "(");
            phones = StringUtils.remove(phones, " ");
        }
        return phones;
    }
}
