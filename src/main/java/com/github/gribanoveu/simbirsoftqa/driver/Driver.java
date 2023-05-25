package com.github.gribanoveu.simbirsoftqa.driver;

import com.github.gribanoveu.simbirsoftqa.enums.Browsers;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
    public static void getDriver(Browsers browsersName, boolean isRemoteDriver) {
        try {
            if (isRemoteDriver) {
                log.info("Запрошен запуск тестов на удаленной машине");
                webDriver = createRemoteDriver(browsersName);
            } else {
                webDriver = createLocalDriver(browsersName);
            }
        } catch (MalformedURLException e) {
            log.error("[!] Получен некорректный URL для запуска");
            throw new RuntimeException("[!] Некорректный URL для запуска!");
        }
    }

    /** Получить уже созданный экземпляр драйвера */
    public static RemoteWebDriver getInstance() {
        return webDriver;
    }

    /** Запустить удаленный браузер (selenium grid).
     * Для запуска можно использовать параметр в мавене - isRemote=true
     * или выбрать Run Remote из панели профилей мавен и обновить проект (maven reload) */
    private static RemoteWebDriver createRemoteDriver(Browsers browserType) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browserType.toString());
        capabilities.setPlatform(Platform.ANY);
        capabilities.acceptInsecureCerts();

        return new RemoteWebDriver(new URL(REMOTE_URL), capabilities);
    }

    /** Запустить локальный браузер */
    private static RemoteWebDriver createLocalDriver(Browsers browserType) {
        switch (browserType) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                var options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("ignore-certificate-errors");
                return new ChromeDriver(options);
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser type");
        }
    }
}
