package com.github.gribanoveu.simbirsoftqa.elements;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public class SelectMenu extends BaseElement {
    public SelectMenu(String byXpath) {
        super(byXpath);
    }

    public static SelectMenu byXpath(String xpath) {
        log.info("WebElement: " + xpath);
        return new SelectMenu(xpath);
    }

    public static SelectMenu byXpath(String xpath, String name) {
        log.info("WebElement: " + xpath);
        elementName = name;
        return new SelectMenu(xpath);
    }

    public SelectMenu selectOptionByValue(String optionName) {
        log.info("Выбрать значение из списка: " + webElement);
        Select select = new Select(webElement);
        select.selectByValue(optionName);
        addAllureEvent("Выбрать значение из списка");
        return this;
    }

    public SelectMenu selectOptionByVisibleText(String text) {
        log.info("Выбрать значение из списка: " + webElement);
        Select select = new Select(webElement);
        select.selectByVisibleText(text);
        addAllureEvent("Выбрать значение из списка");
        return this;
    }
}
