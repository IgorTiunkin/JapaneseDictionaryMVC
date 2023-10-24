package com.phantom.japanese_dictionary_mvc.pageObject;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class GrammarIndexPage extends BasePage{
    public GrammarIndexPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    private By submitButtonBy = By.xpath("//input[(@type='submit') and (@value='Поиск')]");
    private By wordBoxBy = By.xpath("//input[@id='word']");
    private By tableResultBy = By.xpath("//table[@id='table']");

    public GrammarIndexPage inputText(String wordToFind){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(wordBoxBy)).sendKeys(wordToFind);
        return this;
    }

    public GrammarIndexPage goToPage(int pageNumber) {
        By linkToLastPage = By.xpath(String.format("//a[@href='/grammar/show?page=%d']", pageNumber));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(linkToLastPage)).click();
        return this;
    }

    public GrammarIndexPage submit () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return this;
    }
}
