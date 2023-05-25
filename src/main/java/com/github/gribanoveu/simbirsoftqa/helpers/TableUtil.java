package com.github.gribanoveu.simbirsoftqa.helpers;

import com.github.gribanoveu.simbirsoftqa.base.Driver;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @author Evgeny Gribanov
 * @version 24.05.2023
 */
@Slf4j
public class TableUtil {
    /**
     * Извлечь строки таблицы и сохранить из в виде ArrayList.
     * @param tableTbodyLocator локатор до тела таблицы, при передаче только таблицы заголовок будет сохранен как первая строка.
     * @return строки таблицы в виде List<String>
     */
    @Step("Спарсить данные из таблицы")
    public static Map<Integer, List<String>> parseTableDataToMap(String tableTbodyLocator)  {
        Wait.visibleAndClickable(tableTbodyLocator); // ожидаем появления таблицы
        Map<Integer, List<String>> tableMap = new HashMap<>(); // создаем Map для хранения итогового результата

        var tableTbodyContent = Driver.getInstance().findElement(By.xpath(tableTbodyLocator)); // извлекаем всю целиком
        var rows = tableTbodyContent.findElements(By.tagName("tr")); // получаем элементы строки

        int rowCounter = 1;
        for (var row : rows) { // проходим по всем строкам
            var cols = row.findElements(By.tagName("td")); // получаем ячейки
            List<String> cellValues = new ArrayList<>(); // создаем List для сохранения данных строки
            for (var col : cols)  // проходим по ячейкам в строке
                cellValues.add(col.getText().trim()); // сохраняем все данные из колонки в лист
            tableMap.put(rowCounter, cellValues); // добавляем ключ как номер строки и данные из колонок как значения
            rowCounter++;
        }

        log.info("Полученная Map после парсинга таблицы: {}", tableMap);
        return tableMap;
    }

    /**
     * Конвертировать данные таблицы 'История транзакций' из Map<Integer, List<String>> в поток байтов для прикрепления в Allure.
     * @param data Map с данными вида: Date, Amount, Type.
     * @return данные в виде ByteArray
     */
    @Step("Конвертировать данные таблицы в ByteArray")
    public static InputStream convertTransactionTableDataFromMapToByteStream(Map<Integer, List<String>> data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (List<String> transaction : data.values()) {
                String date = transaction.get(0);
                String amount = transaction.get(1);
                String type = transaction.get(2);
                String formattedDate = StringUtil.convertDateFormat(date);
                String row = String.format("%s,%s,%s\n", formattedDate, amount, type);
                outputStream.write(row.getBytes());
            }
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (ParseException | IOException e) {
            log.error("Ошибка во время конвертации Map в ByteArray, проверьте исходный формат");
        }
        return null;
    }

    @Test(groups = "util")
    private void convertTransactionTableDataFromMapToByteStreamShouldReturnExpectedResult() {
        var currentMap = new HashMap<Integer, List<String>>();
        currentMap.put(1, Arrays.asList("May 25, 2023 10:17:00 AM", "75025", "Debit"));
        var expectedParseResult = "25 мая 2023 10:17:00,75025,Debit";

        var convertResult = convertTransactionTableDataFromMapToByteStream(currentMap);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(convertResult))) {
            Assertions.assertThat(reader.readLine())
                    .as("Строка после парсинга, не соответствует ожидаемой")
                    .isEqualTo(expectedParseResult);
        } catch (IOException e) {
            log.error("Ошибка при чтении потока байтов");
        }
    }

}
