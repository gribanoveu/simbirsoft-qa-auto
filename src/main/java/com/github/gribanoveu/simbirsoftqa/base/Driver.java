package com.github.gribanoveu.simbirsoftqa.base;

import com.github.gribanoveu.simbirsoftqa.enums.Browsers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public abstract class Driver {
    private static final String REMOTE_URL = "http://localhost:4444/wd/hub";

    /** Единственный экземпляр вебдрайвера */
    protected static RemoteWebDriver webDriver;

    /**
     * Запустить указанный браузер.
     * @param browsersName название браузера
     * @param isRemoteDriver флаг запускать ли удаленно
     * @return экземпляр настроенного webDriver
     */
    public static RemoteWebDriver getDriver(Browsers browsersName, boolean isRemoteDriver) {
        var capabilities = switch (browsersName) {
            case CHROME -> Browser.getChromeRemoteWebDriver();
            case FIREFOX -> Browser.getFirefoxRemoteWebDriver();
            case EDGE -> Browser.getEdgeRemoteWebDriver();
        };

        try {
            if (isRemoteDriver) {
                log.info("Запрошен запуск тестов на удаленной машине");
                return new RemoteWebDriver(new URL(REMOTE_URL), capabilities);
            }
        } catch (MalformedURLException e) {
            log.error("[!] Получен некорректный URL для запуска");
            throw new RuntimeException("[!] Некорректный URL для запуска!");
        }

        return webDriver;
    }

    /** Получить уже созданный экземпляр драйвера */
    public static RemoteWebDriver getInstance() {
        return webDriver;
    }
}
