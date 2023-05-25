package com.github.gribanoveu.simbirsoftqa.service;

import com.github.gribanoveu.simbirsoftqa.driver.Driver;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public class AllureAttachments {
    private static final String FORMATTED_TIME_PATTERN = "dd MMMM yyyy - H:mm";
    private static final String TIMEZONE = "Europe/Moscow";

    /** Прикрепляет текстовое сообщение в отчет */
    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    /** Прикрепляет скриншот в отчет */
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] attachScreenshot(String attachName) {
        return ((TakesScreenshot) Driver.getInstance()).getScreenshotAs(OutputType.BYTES);
    }

    /** Заполняет окружение в отчете */
    public static void getAllureEnvironment() {
        var timeZone = LocalDateTime.now().atZone(ZoneId.of(TIMEZONE));
        var formattedTime = DateTimeFormatter.ofPattern(FORMATTED_TIME_PATTERN).format(timeZone);
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Браузер", Driver.getInstance().getCapabilities().getBrowserName().toUpperCase())
                        .put("Версия браузера", Driver.getInstance().getCapabilities().getBrowserVersion())
                        .put("Время запуска", formattedTime)
                        .build());
    }
}
