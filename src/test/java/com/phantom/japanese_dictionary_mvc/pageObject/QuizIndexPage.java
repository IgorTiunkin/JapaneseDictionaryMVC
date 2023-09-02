package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuizIndexPage extends BasePage{

    private By numberOfTasksBy = By.xpath("//input[@id='numberOfTasks']");
    private By numberOfOptionsBy = By.xpath("//input[@id='numberOfOptions']");
    private By submitButtonBy = By.xpath("//input[@value='Выбор']");

    public QuizIndexPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public QuizIndexPage inputNumberOfTasks (String numberOfTasks) {
        WebElement box = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(numberOfTasksBy));
        box.clear();
        box.sendKeys(numberOfTasks);
        return this;
    }

    public QuizIndexPage inputNumberOfOptions (String numberOfOptions) {
        WebElement box = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(numberOfOptionsBy));
        box.clear();
        box.sendKeys(numberOfOptions);
        return this;
    }

    public QuizShowPage submit() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return new QuizShowPage(driver, webDriverWait);
    }
}
