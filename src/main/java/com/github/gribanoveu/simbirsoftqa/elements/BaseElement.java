package com.github.gribanoveu.simbirsoftqa.elements;

import com.github.gribanoveu.simbirsoftqa.driver.Driver;
import com.github.gribanoveu.simbirsoftqa.helpers.Wait;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public abstract class BaseElement {
    private static final String ELEMENT_TITLE_ATTRIBUTE = "title";

    /** Получение веб элемента */
    protected WebElement webElement;
    protected static Actions actions;
    protected static String elementName;

    /** Установка ожидания, по истечении которого происходит исключение */
    private static final Duration DURATION_TIMEOUT = Duration.ofSeconds(10);

    /** Установка ожидания перерыва между повторным поиском */
    private static final Duration DURATION_SLEEP = Duration.ofSeconds(1);

    /**  Базовый элемент
     * @param byXpath - локатор элемента
     * В конструкторе инициализируется длительность ожидания появления элемента
     * и осуществляется поиск элемента */
    public BaseElement(String byXpath) {
        Wait.initWait(DURATION_TIMEOUT, DURATION_SLEEP);
        Wait.visibleAndClickable(byXpath);
        webElement = Driver.getInstance().findElement(By.xpath(byXpath));
        actions = new Actions(Driver.getInstance());
    }

    /** Получение оборачиваемого элемента
     * Получив оборачиваемый элемент, можно вызвать его методы
     * Например, getText() */
    public WebElement getWebElement() {
        return webElement;
    }

    /**
     * Добавить шаг в аллюр. Если при создании элемента указывается его название, то использовать его,
     * иначе попытаться получить название через атрибут title
     * @param eventDescription название события, что происходит над элементом
     */
    protected void addAllureEvent(String eventDescription) {
        if (elementName == null)
            Allure.step(String.format("%s: '%s'", eventDescription, webElement.getAttribute(ELEMENT_TITLE_ATTRIBUTE)));
        else
            Allure.step(String.format("%s '%s'", eventDescription, elementName));
    }

}
