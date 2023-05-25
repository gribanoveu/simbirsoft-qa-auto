package com.github.gribanoveu.simbirsoftqa.elements;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

/**
 * @author Evgeny Gribanov
 * @version 24.05.2023
 */
@Slf4j
public class Div extends BaseElement {
    public Div(String byXpath) {
        super(byXpath);
    }

    public static Div byXpath(String xpath) {
        log.info("WebElement: " + xpath);
        return new Div(xpath);
    }

    public static Div byXpath(String xpath, String name) {
        log.info("WebElement: " + xpath);
        elementName = name;
        return new Div(xpath);
    }

    public String getText() {
        return getWebElement().getText();
    }

    public Div assertThatElementIsDisplayed() {
        Assertions.assertThat(getWebElement().isDisplayed())
                .as("Элемент не отображается на странице")
                .isTrue();
        return this;
    }
}
