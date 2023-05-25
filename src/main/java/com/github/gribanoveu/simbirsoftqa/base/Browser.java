package com.github.gribanoveu.simbirsoftqa.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class Browser extends Driver {
    /** Версия браузеров для selenoid, данные версии должны быть скачана на инстансе selenoid (browsers.json) */
    private static final String REMOTE_BROWSER_VERSION = "101.0";

    /** Возвращает экземпляр вебдрайвера для запуска Google Chrome */
    public static DesiredCapabilities getChromeRemoteWebDriver() {
        // инициализация нужной версии вебдрайвера в зависимости от установленной версии браузера
        WebDriverManager.chromedriver().setup();

        // установка состояния браузера
        var options = new ChromeOptions();

        options.addArguments("--incognito");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("ignore-certificate-errors");


        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("screenResolution", "1920x1080x24");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        webDriver = new ChromeDriver(options);
        return capabilities;
    }

    /** Возвращает экземпляр вебдрайвера для запуска Microsoft Edge */
    public static DesiredCapabilities getEdgeRemoteWebDriver() {
        // инициализация нужной версии вебдрайвера в зависимости от установленной версии браузера
        WebDriverManager.edgedriver().setup();

        // установка состояния браузера
        var options = new EdgeOptions();
        options.addArguments("--incognito");

        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "edge");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("screenResolution", "1920x1080x24");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        webDriver = new EdgeDriver(options);
        return capabilities;
    }

    /** Возвращает экземпляр вебдрайвера для запуска Google Chrome */
    public static DesiredCapabilities getFirefoxRemoteWebDriver() {
        // инициализация нужной версии вебдрайвера в зависимости от установленной версии браузера
        WebDriverManager.firefoxdriver().setup();

        // установка состояния браузера
        var options = new FirefoxOptions();
        options.addArguments("-private");

        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "firefox");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("screenResolution", "1920x1080x24");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        webDriver = new FirefoxDriver(options);
        return capabilities;
    }
}
