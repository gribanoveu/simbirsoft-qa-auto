package com.github.gribanoveu.simbirsoftqa.pages;

import com.github.gribanoveu.simbirsoftqa.elements.Button;
import com.github.gribanoveu.simbirsoftqa.elements.SelectMenu;
import com.github.gribanoveu.simbirsoftqa.helpers.Navigate;
import io.qameta.allure.Step;

/**
 * @author Evgeny Gribanov
 * @version 23.05.2023
 */
public class LoginPage {
    private static final String LOGIN_PAGE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final String CUSTOMER_BUTTON_LOCATOR = "//button[contains(text(),'Customer')]";
    private static final String CUSTOMER_NAME_SELECT_LOCATOR = "//select[contains(@name,'user')]";
    private static final String LOGIN_BUTTON_LOCATOR = "//button[contains(text(),'Login')]";

    @Step("Когда юзер открыл страницу логина")
    public LoginPage whenOpenLoginPage() {
        Navigate.openPage(LOGIN_PAGE_URL);
        return this;
    }

    @Step("Выбрал логин как клиент")
    public LoginPage selectLoginAsCustomer() {
        Button.byXpath(CUSTOMER_BUTTON_LOCATOR, "Customer Login").click();
        return this;
    }

    @Step("В меню выбора пользователя выбрал: {userName}")
    public LoginPage selectUserForLogin(String userName) {
        SelectMenu.byXpath(CUSTOMER_NAME_SELECT_LOCATOR, userName).selectOptionByVisibleText(userName);
        return this;
    }

    @Step("Нажал кнопку войти")
    public LoginPage pressLoginButton() {
        Button.byXpath(LOGIN_BUTTON_LOCATOR, "Login").click();
        return this;
    }
}
