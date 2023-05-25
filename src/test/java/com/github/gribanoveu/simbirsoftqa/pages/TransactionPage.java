package com.github.gribanoveu.simbirsoftqa.pages;

import com.github.gribanoveu.simbirsoftqa.elements.Button;
import com.github.gribanoveu.simbirsoftqa.helpers.TableUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

/**
 * @author Evgeny Gribanov
 * @version 24.05.2023
 */
public class TransactionPage {
    private static final String TABLE_CONTENT_LOCATOR = "//table/tbody";
    private static final String RESET_TRANSACTION_BUTTON_LOCATOR = "//button[contains(text(),'Reset') and @ng-click]";
    private static final String LOGOUT_BUTTON_LOCATOR = "//button[contains(text(),'Logout') and @ng-click]";

    @Step("Проверить, что в таблице транзакций существует две транзакции")
    public TransactionPage checkThatDebitAndWithdrawExistWithSum(String transactionSum) {
        var result = TableUtil.parseTableDataToMap(TABLE_CONTENT_LOCATOR);
        var softAssert = new SoftAssertions();

        softAssert.assertThat(result).as("Проверьте, существует ли ровно 2 транзакции")
                .hasSize(2);
        softAssert.assertThat(result.get(1)).as("Проверьте, существует ли транзакция Credit")
                .containsSubsequence(transactionSum, "Credit");
        softAssert.assertThat(result.get(2)).as("Проверьте, существует ли транзакция Debit")
                .containsSubsequence(transactionSum, "Debit");

        softAssert.assertAll();
        return this;
    }

    @Step("Прикрепить список транзакций к отчету")
    public TransactionPage attachCsvFileToAllureReport() {
        var tableContent = TableUtil.parseTableDataToMap(TABLE_CONTENT_LOCATOR);
        Allure.addAttachment("transactions_history", "text/csv", TableUtil.convertTransactionTableDataFromMapToByteStream(tableContent), ".csv");
        return this;
    }

    @Step("Нажать очистить историю и выйти")
    public TransactionPage clickResetTransactionsAndLogout() {
        Button.byXpath(RESET_TRANSACTION_BUTTON_LOCATOR, "Reset").click();
        Button.byXpath(LOGOUT_BUTTON_LOCATOR, "Logout").click();
        return this;
    }

}
