package com.github.gribanoveu.simbirsoftqa.helpers;

import com.github.gribanoveu.simbirsoftqa.base.Driver;
import io.qameta.allure.Allure;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class Navigate {
    /** Открытие новой страницы */
    public static void openPage(String URL) {
        Driver.getInstance().navigate().to(URL);
        Allure.step("Открыть страницу: " + Driver.getInstance().getTitle());
    }

    /** Переход на предыдущую страницу */
    public static void back() {
        Driver.getInstance().navigate().back();
        Allure.step("Переход на страницу назад: " + Driver.getInstance().getTitle());
    }

    /** Переход на следующую страницу */
    public static void forward() {
        Driver.getInstance().navigate().forward();
        Allure.step("Переход на страницу вперед: " + Driver.getInstance().getTitle());
    }

    /** Обновление страницы */
    public static void refresh() {
        Driver.getInstance().navigate().refresh();
        Allure.step("Обновить страницу");
    }
}
