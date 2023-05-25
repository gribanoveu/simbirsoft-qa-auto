package com.github.gribanoveu.simbirsoftqa.pages;

import com.github.gribanoveu.simbirsoftqa.elements.Button;
import com.github.gribanoveu.simbirsoftqa.elements.Div;
import com.github.gribanoveu.simbirsoftqa.elements.TextBox;
import com.github.gribanoveu.simbirsoftqa.helpers.StringUtil;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class AccountPage {
    private static final String DEPOSIT_BUTTON_LOCATOR = "//button[contains(text(),'Deposit') and @ng-click]";
    private static final String WITHDRAW_BUTTON_LOCATOR = "//button[contains(text(),'Withdrawl') and @ng-click]";
    private static final String TRANSACTIONS_BUTTON_LOCATOR = "//button[contains(text(),'Transactions') and @ng-click]";

    private static final String DEPOSIT_INPUT_LOCATOR = "//label[contains(text(),'Deposited')]/following-sibling::input";
    private static final String WITHDRAW_INPUT_LOCATOR = "//label[contains(text(),'Withdraw')]/following-sibling::input";

    private static final String CONFIRM_DEPOSIT_BUTTON = "//button[contains(text(),'Deposit') and @type='submit']";
    private static final String CONFIRM_WITHDRAW_BUTTON = "//button[contains(text(),'Withdraw') and @type='submit']";

    private static final String ACCOUNT_STATUS_TEXT_LOCATOR = "//div[contains(@ng-hide,'Account') and contains(text(),'Account')]";
    private static final String TRANSACTION_STATUS_LOCATOR = "//div[@class='ng-scope']/span[contains(@class,'error')]";

    private static final String TRANSACTION_DEPOSIT_CONFIRM_TEXT = "Deposit Successful";
    private static final String TRANSACTION_WITHDRAW_CONFIRM_TEXT = "Transaction successful";
    private static final String TABLE_LOCATOR = "//table";

    @Step("Нажал кнопку для пополнения счета")
    public AccountPage whenPressDepositButton() {
        Button.byXpath(DEPOSIT_BUTTON_LOCATOR, "Deposit").click();
        return this;
    }

    @Step("Нажал кнопку для снятия денег со счета")
    public AccountPage whenPressWithdrawnButton() {
        Button.byXpath(WITHDRAW_BUTTON_LOCATOR, "Withdrawl").click();
        return this;
    }

    @Step("Внес депозит, равный {depositValue}")
    public AccountPage andEnterMoneyToDeposit(String depositValue) {
        TextBox.byXpath(DEPOSIT_INPUT_LOCATOR, "Amount to be Deposited")
                .click().clear().enterText(depositValue);
        Button.byXpath(CONFIRM_DEPOSIT_BUTTON, "Confirm")
                .click();

        var status = Div.byXpath(TRANSACTION_STATUS_LOCATOR).getText();
        Assertions.assertThat(status).as("Ожидалось, что статус транзакции депозита успешен, но это не так")
                .isEqualTo(TRANSACTION_DEPOSIT_CONFIRM_TEXT);
        return this;
    }

    @Step("Выполнил списание, равное {withdrawnValue}")
    public AccountPage andWithdrawnMoneyFromAccount(String withdrawnValue) {
        TextBox.byXpath(WITHDRAW_INPUT_LOCATOR, "Amount to be Withdrawn")
                .click().clear().enterText(withdrawnValue);
        Button.byXpath(CONFIRM_WITHDRAW_BUTTON, "Withdraw")
                .click();

        var status = Div.byXpath(TRANSACTION_STATUS_LOCATOR).getText();
        Assertions.assertThat(status).as("Ожидалось, что статус транзакции снятия успешен, но это не так")
                .isEqualTo(TRANSACTION_WITHDRAW_CONFIRM_TEXT);
        return this;
    }

    @Step("Проверить, что сумма на счете равна нулю")
    public AccountPage checkThatSumOnAccountEqualsZero() {
        var value = Div.byXpath(ACCOUNT_STATUS_TEXT_LOCATOR, "Account information panel").getText();
        var balance = StringUtil.getValueFromStringAsMap(value, "Balance");

        Assertions.assertThat(balance).as("Ожидалось, что баланс на аккаунте будет равен нулю, но это не так")
                .isEqualTo("0");
        return this;
    }

    @Step("Открыть страницу 'История транзакций'")
    public AccountPage andOpenTransactionsHistory() {
        Button.byXpath(TRANSACTIONS_BUTTON_LOCATOR, "Transactions").click();
        Div.byXpath(TABLE_LOCATOR).assertThatElementIsDisplayed();
        return this;
    }
}
