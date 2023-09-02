package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GrammarIndexPage extends BasePage{
    public GrammarIndexPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    private By submitButtonBy = By.xpath("//input[(@type='submit') and (@value='Поиск')]");
    private By wordBoxBy = By.xpath("//input[@id='word']");

    public GrammarIndexPage inputText(String wordToFind){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(wordBoxBy)).sendKeys(wordToFind);
        return this;
    }

    public void submit () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
    }
}
