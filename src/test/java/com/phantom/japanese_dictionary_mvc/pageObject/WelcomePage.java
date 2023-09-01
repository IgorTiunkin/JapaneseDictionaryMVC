package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomePage {

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private By dictionaryBy = By.xpath("//a[@href='/dictionary']");
    private By grammarBy = By.xpath("//a[@href='/grammar']");
    private By writeTestBy = By.xpath("//a[@href='/write-test']");
    private By quizBy = By.xpath("//a[@href='/quiz']");

    public WelcomePage (WebDriver webDriver, WebDriverWait webDriverWait) {
        this.driver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    public void goToDictionary() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(dictionaryBy)).click();
    }

    public void goToGrammar() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(grammarBy)).click();
    }

    public void goToWriteTest() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(writeTestBy)).click();
    }

    public void goToQuiz() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(quizBy)).click();
    }

}
