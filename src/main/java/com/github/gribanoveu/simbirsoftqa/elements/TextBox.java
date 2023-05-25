package com.github.gribanoveu.simbirsoftqa.elements;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public class TextBox extends BaseElement {
    public TextBox(String byXpath) {
        super(byXpath);
    }

    public static TextBox byXpath(String xpath) {
        log.info("WebElement: " + xpath);
        return new TextBox(xpath);
    }

    public static TextBox byXpath(String xpath, String name) {
        log.info("WebElement: " + xpath);
        elementName = name;
        return new TextBox(xpath);
    }

    public TextBox click() {
        log.info("Клик по тестовому полю: " + webElement);
        actions.moveToElement(webElement).click().perform();
        addAllureEvent("Клик по текстовому полю");
        return this;
    }


    public TextBox clear() {
        log.info("Очистить текстовое поле");
        webElement.clear();
        Allure.step("Очистить данные");
        return this;
    }

    public TextBox enterText(String text) {
        log.info("Ввести текст в текстовое поле: " + text);
        webElement.sendKeys(text);
        Allure.step("Ввести текст: " + text);
        return this;
    }

    public TextBox pressEnter() {
        log.info("Нажать ENTER");
        webElement.sendKeys(Keys.ENTER);
        Allure.step("Нажать ENTER");
        return this;
    }

    public TextBox selectText() {
        log.info("Выделить текст");
        webElement.sendKeys(Keys.CONTROL + "a");
        Allure.step("Выделить текст");
        return this;
    }
}
