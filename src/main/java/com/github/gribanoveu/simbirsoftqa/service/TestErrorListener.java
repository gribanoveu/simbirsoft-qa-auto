package com.github.gribanoveu.simbirsoftqa.service;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Слушатель для анализа выполнения тестов.
 * В случае неудачи прикрепляет скриншот в allure.
 * Работает только при запуске через maven или xml файл.
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Slf4j
public class TestErrorListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        AllureAttachments.attachScreenshot("SCREENSHOT-FAIL");
        AllureAttachments.attachAsText("INFO", getErrorMessage(result));
        log.info(String.format("[!] LOG: %s - %s тест завершился неудачно", result.getInstanceName().toUpperCase(), result.getMethod().getMethodName()));
        log.info("Скриншот страницы сохранен");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        AllureAttachments.attachScreenshot("SCREENSHOT-SKIP");
        AllureAttachments.attachAsText("INFO", getErrorMessage(result));
        log.info(String.format("[!] LOG: %s - %s тест пропущен", result.getInstanceName().toUpperCase(), result.getName()));
        log.info("Скриншот страницы сохранен");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(String.format("[!] LOG: %s - %s успех", result.getInstanceName().toUpperCase(), result.getName()));
    }

    private String getErrorMessage(ITestResult result) {
        return String.format("Тест завершился неудачно или пропущен: %s. Ошибка при выполнении метода: %s",
                result.getInstanceName(), result.getMethod().getMethodName());
    }
}


