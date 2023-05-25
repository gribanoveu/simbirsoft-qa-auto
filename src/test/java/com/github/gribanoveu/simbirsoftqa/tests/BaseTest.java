package com.github.gribanoveu.simbirsoftqa.tests;

import com.github.gribanoveu.simbirsoftqa.base.Driver;
import com.github.gribanoveu.simbirsoftqa.enums.Browsers;
import com.github.gribanoveu.simbirsoftqa.helpers.Wait;
import com.github.gribanoveu.simbirsoftqa.service.AllureAttachments;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public abstract class BaseTest {
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String IS_REMOTE_DRIVER = System.getProperty("isRemote", "false");

    private static final Duration DURATION_TIMEOUT = Duration.ofSeconds(10); // время поиска элементов
    private static final Duration DURATION_SLEEP = Duration.ofSeconds(1); // ожидание перед повторным поиском

    /** Метод инициализации вебдрайвера, запускается перед выполнением тестов.
     * Получает название браузера.
     * Получает указание запускать ли удаленный веб драйвер */
    @BeforeSuite(description = "Запустить браузер")
    public static void runBrowser() {
        Driver.getDriver(Browsers.fromString(BROWSER), Boolean.valueOf(IS_REMOTE_DRIVER));
        Driver.getInstance().manage().window().maximize();
        Wait.initWait(DURATION_TIMEOUT, DURATION_SLEEP);
        AllureAttachments.getAllureEnvironment();
        log.info("Драйвер стартовал!");
    }

    /* Метод закрытия экземпляра драйвера, выполняется после всех тестов */
    @AfterSuite(description = "Закрыть браузер")
    public static void setDown() {
        if(Driver.getInstance() != null) {
            Driver.getInstance().quit();
            log.info("Драйвер остановлен!");
        }
    }
}
