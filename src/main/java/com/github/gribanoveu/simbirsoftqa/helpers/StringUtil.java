package com.github.gribanoveu.simbirsoftqa.helpers;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author Evgeny Gribanov
 * @version 24.05.2023
 */
@Slf4j
public class StringUtil {
    private static final String ORIGINAL_DATE_FORMAT_PATTERN = "MMMM d, yyyy h:mm:ss a";
    private static final String TARGET_DATE_FORMAT_PATTERN = "dd MMMM yyyy HH:mm:ss";
    public static final String PARSE_PATTERN = "\\s*:\\s*";
    public static final String PARSE_DELIMITER = "\\s*,\\s*";

    /**
     * Разбить строку на ключ-значение и получить значение по ключу.
     * @param string исходная строка в формате [key : value , key : value]
     * @param value искомый ключ
     * @return значение искомого ключа
     */
    public static String getValueFromStringAsMap(String string, String value) {
        var pattern = Pattern.compile(PARSE_PATTERN);
        var mapValues = new HashMap<String, String>();
        var entries = string.split(PARSE_DELIMITER);
        for (String entry : entries) {
            var keyValue = pattern.split(entry);
            mapValues.put(keyValue[0], keyValue[1]);
        }
        return mapValues.get(value);
    }

    /**
     * Форматировать строку с датой из формата: May 24, 2023 5:02:36 PM, в формат: 24 май 2023 17:02:36
     * @param dateString строка с исходным форматом.
     * @return строка с ожидаемым форматом
     * @throws ParseException ошибка при неверном формате исходной строки или неверном паттерне для форматера
     */
    public static String convertDateFormat(String dateString) throws ParseException {
        var originalFormat = new SimpleDateFormat(ORIGINAL_DATE_FORMAT_PATTERN, Locale.ENGLISH);
        var targetFormat = new SimpleDateFormat(TARGET_DATE_FORMAT_PATTERN);
        var date = originalFormat.parse(dateString);
        return targetFormat.format(date);
    }


    @Test(groups = "util")
    private void getValueFromStringAsMapShouldReturnExpectedValues() {
        var originalString = "Account number : 12345 , Currency : Dollar";
        var accountKey = "Account number";
        var currencyKey = "Currency";

        var resultAccount = getValueFromStringAsMap(originalString, accountKey);
        var resultCurrency = getValueFromStringAsMap(originalString, currencyKey);

        var softAssert = new SoftAssertions();
        softAssert.assertThat(resultAccount).isEqualTo("12345");
        softAssert.assertThat(resultCurrency).isEqualTo("Dollar");
        softAssert.assertAll();
    }

    @Test(groups = "util")
    private void convertDataFormatShouldReturnExpectedResult() throws ParseException {
        var currentDate = "May 24, 2023 6:20:31 PM";
        var expectedDate = "24 мая 2023 18:20:31";

        var convertResult = convertDateFormat(currentDate);

        Assertions.assertThat(convertResult)
                .as("Неверный формат в процессе парсинга даты")
                .isEqualTo(expectedDate);
    }

}
