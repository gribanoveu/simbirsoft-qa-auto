package com.github.gribanoveu.simbirsoftqa.helpers;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class FibonacciUtils {
    /**
     * Берем текущую дату и вычисляем значение числа Фибоначчи,
     * дата равна номеру в последовательности.
     * @return значение в последовательности согласно его номеру.
     */
    public static String getNumberWhenSequenceNumberIsToday() {
        var currentDate = LocalDate.now();
        var sequenceNumber = currentDate.getDayOfMonth() + 1;
        return Integer.toString(getFibonacciValue(sequenceNumber));
    }

    /**
     * Вычисляем число Фибоначчи с помощью рекурсии. Т.к максимальное число может быть 32 (31 день в месяце + 1),
     * то можно использовать рекурсию, несмотря на более долгий расчет, как наиболее оптимальное решение.
     * @param number порядковый номер числа
     * @return значение в последовательности
     */
    private static int getFibonacciValue(int number) {
        if (number <= 1) return 0;
        else if (number == 2) return 1;
        else return getFibonacciValue(number - 1) + getFibonacciValue(number - 2);
    }

    @Test(groups = "util" )
    private void getFibonacciValueFunctionShouldReturnExpectedValue() {
        var sequenceNumber = 9;
        var expectedFibonacciValue = 21;
        var result = getFibonacciValue(sequenceNumber);
        Assertions.assertThat(result)
                .as("9-е чисто Фибоначчи должно быть равно 21")
                .isEqualTo(expectedFibonacciValue);
    }
}
