package ru.sbtqa.tag.pagefactory2example.stepdefs.actions;

import org.openqa.selenium.WebElement;

import java.util.Objects;

public class CustomChecks {

    public static boolean isDisplayed(WebElement element) {
        Objects.requireNonNull(element, "ссылка на переданный элемент = null");
        try {
            return element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
