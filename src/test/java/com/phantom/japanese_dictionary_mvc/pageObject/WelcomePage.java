package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage extends BasePage{

    private By dictionaryBy = By.xpath("//a[@href='/dictionary']");
    private By grammarBy = By.xpath("//a[@href='/grammar']");
    private By writeTestBy = By.xpath("//a[@href='/write-test']");
    private By quizBy = By.xpath("//a[@href='/quiz']");
    private By logoutBy = By.xpath("//a[@href='/logout']");

    public WelcomePage (WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public DictionaryIndexPage goToDictionary() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(dictionaryBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public GrammarIndexPage goToGrammar() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(grammarBy)).click();
        return new GrammarIndexPage(driver, webDriverWait);
    }

    public WritePracticeIndexPage goToWriteTest() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(writeTestBy)).click();
        return new WritePracticeIndexPage(driver, webDriverWait);
    }

    public void goToQuiz() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(quizBy)).click();
    }

    public LoginPage logout() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(logoutBy)).click();
        return new LoginPage(driver, webDriverWait);
    }

}
