package com.github.gribanoveu.simbirsoftqa.tests;

import com.github.gribanoveu.simbirsoftqa.helpers.FibonacciUtils;
import com.github.gribanoveu.simbirsoftqa.pages.AccountPage;
import com.github.gribanoveu.simbirsoftqa.pages.LoginPage;
import com.github.gribanoveu.simbirsoftqa.pages.TransactionPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;


/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
@Epic("Smoke")
@Feature("Транзакции")
public class XyzBankTransactionsSmokeTest extends BaseTest {
    private static final String USER_NAME = "Harry Potter";
    private static final String SUM_FROM_FIBONACCI_ALGORITHM = FibonacciUtils.getNumberWhenSequenceNumberIsToday();

    @Test(description = "Юзер вошел на сайт как Harry Potter и выполнил две транзакции")
    @Story("Юзер вошел на сайт и провел две транзакции")
    @Description("Юзер вошел на сайт, выполнил пополнение и списание со счета. В истории транзакций отображаются обе транзакции")
    void userLoggedAsCustomerHarryPotterAndPerformedTwoTransactions()  {
        new LoginPage()
                .whenOpenLoginPage()
                .selectLoginAsCustomer()
                .selectUserForLogin(USER_NAME)
                .pressLoginButton();
        new AccountPage()
                .whenPressDepositButton()
                .andEnterMoneyToDeposit(SUM_FROM_FIBONACCI_ALGORITHM)
                .whenPressWithdrawnButton()
                .andWithdrawnMoneyFromAccount(SUM_FROM_FIBONACCI_ALGORITHM)
                .checkThatSumOnAccountEqualsZero()
                .andOpenTransactionsHistory();
        new TransactionPage()
                .checkThatDebitAndWithdrawExistWithSum(SUM_FROM_FIBONACCI_ALGORITHM)
                .attachCsvFileToAllureReport()
                .clickResetTransactionsAndLogout();

    }

}
