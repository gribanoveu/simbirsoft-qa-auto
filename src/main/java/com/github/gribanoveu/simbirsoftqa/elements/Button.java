package com.github.gribanoveu.simbirsoftqa.elements;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public class Button extends BaseElement {
    private Button(String byXpath) {
        super(byXpath);
    }

    public static Button byXpath(String xpath) {
        log.info("WebElement: " + xpath);
        return new Button(xpath);
    }

    public static Button byXpath(String xpath, String name) {
        log.info("WebElement: " + xpath);
        elementName = name;
        return new Button(xpath);
    }

    public Button click() {
        log.info("Клик по кнопке: " + webElement);
        actions.moveToElement(webElement).click().perform();
        addAllureEvent("Клик по кнопке");
        return this;
    }

    public Button contextClick() {
        log.info("Контекстный клик");
        actions.moveToElement(webElement).contextClick().perform();
        addAllureEvent("Контекстный клик по кнопке");
        return this;
    }

    public Button doubleClick() {
        log.info("Двойной клик по кнопке: " + webElement);
        actions.moveToElement(webElement).doubleClick().perform();
        addAllureEvent("Двойной клик по кнопке");
        return this;
    }

    public Button moveTo() {
        log.info("Навести на кнопку: " + webElement);
        actions.moveToElement(webElement).perform();
        addAllureEvent("Навести на кнопку");
        return this;
    }
}
