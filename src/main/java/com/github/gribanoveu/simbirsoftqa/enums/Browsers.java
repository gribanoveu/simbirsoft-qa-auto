package com.github.gribanoveu.simbirsoftqa.enums;

import lombok.AllArgsConstructor;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@AllArgsConstructor
public enum Browsers {
    CHROME("chrome"),
    FIREFOX("firefox");

    private final String browserName;

    @Override
    public String toString() {
        return String.valueOf(this.browserName);
    }

    /**
     * Получить название браузера для запуска.
     * Если совпадений нет, то запустить хром.
     * @param browserName название в виде строки
     * @return строковое значение с именем браузера из enum
     */
    public static Browsers fromString(String browserName) {
        for (var browser : Browsers.values())
            if (browserName.equalsIgnoreCase(browser.browserName)) return browser;
        return Browsers.CHROME;
    }
}
