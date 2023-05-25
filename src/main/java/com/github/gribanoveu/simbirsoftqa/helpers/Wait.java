package com.github.gribanoveu.simbirsoftqa.helpers;

import com.github.gribanoveu.simbirsoftqa.driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class Wait {
    protected static WebDriverWait wait;

    /** Установка таймаута ожидания и интервал опроса */
    public static void initWait(Duration timeOut, Duration sleep) {
        wait = new WebDriverWait(Driver.getInstance(), timeOut, sleep);
    }

    public static void visibleAndClickable(String webElement) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(webElement)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(webElement)));
    }
}
