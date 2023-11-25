package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
    private By usernameBy = By.id("username");
    private By passwordBy = By.id("password");
    private By submitButtonBy = By.xpath("//input[@value='Войти']");


    public LoginPage (WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public WelcomePage loginUser (String username, String password)  {
        WebElement usernameBox = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        usernameBox.clear();
        usernameBox.sendKeys(username);
        WebElement passwordBox = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordBy));
        passwordBox.clear();
        passwordBox.sendKeys(password);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return new WelcomePage(driver,webDriverWait);
    }

    public WelcomePage loginDefaultUser ()  {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return new WelcomePage(driver,webDriverWait);
    }
}
