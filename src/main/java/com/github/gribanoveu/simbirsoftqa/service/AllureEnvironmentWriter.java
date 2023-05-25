package com.github.gribanoveu.simbirsoftqa.service;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * allure-environment-writer
 * @author automatedowl@yandex.com
 * @version 1.0.1
 * @link <a href="https://github.com/AutomatedOwl/">Github Author</a>
 */
@Slf4j
@SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
public class AllureEnvironmentWriter {
    private static final String ALLURE_RESULTS_ENVIRONMENT_FILE = "/target/allure-results/environment.xml";
    private static final String ALLURE_RESULTS_DIR = "/target/allure-results";
    private static final String SYSTEM_USER_DIRECTORY_PROPERTY = "user.dir";

    /**
     * Записывает переменные окружения в отчет allure
     * @param environmentValuesSet - переменные окружения в формате ключ - значение
     */
    public static void allureEnvironmentWriter(ImmutableMap<String, String> environmentValuesSet)  {
        try {
            var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            var environment = doc.createElement("environment");
            doc.appendChild(environment);

            environmentValuesSet.forEach((k, v) -> {
                var parameter = doc.createElement("parameter");
                var key = doc.createElement("key");
                var value = doc.createElement("value");
                key.appendChild(doc.createTextNode(k));
                value.appendChild(doc.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            // Write the content into xml file
            var transformer = TransformerFactory.newInstance().newTransformer();
            var source = new DOMSource(doc);
            var allureResultsDir =
                    new File(System.getProperty(SYSTEM_USER_DIRECTORY_PROPERTY) + ALLURE_RESULTS_DIR);
            if (!allureResultsDir.exists()) allureResultsDir.mkdirs();
            var result = new StreamResult(
                    new File(System.getProperty(SYSTEM_USER_DIRECTORY_PROPERTY) + ALLURE_RESULTS_ENVIRONMENT_FILE));
            transformer.transform(source, result);
            log.info("[!] Окружение записано в отчет Allure");
        } catch (ParserConfigurationException | TransformerException pce) {
            log.info("[!] Ошибка при записи окружения в отчет Allure");
            pce.printStackTrace();
        }
    }
}
