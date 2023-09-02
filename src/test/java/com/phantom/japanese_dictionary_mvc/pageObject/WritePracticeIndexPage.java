package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WritePracticeIndexPage extends BasePage{

    private By translationRadioBy = By.xpath("//input[@value='TRANSLATION']");
    private By spellingRadioBy = By.xpath("//input[@value='SPELLING']");
    private By kanjiRadioBy = By.xpath("//input[@value='KANJI']");
    private By kanaRadioBy = By.xpath("//input[@value='KANA']");
    private By defaultRadioBy = By.xpath("//input[@value='DEFAULT']");
    private By submitButtonBy = By.xpath("//input[(@type='submit') and (@value='Выбор')]");
    private By numberOfTasksBoxBy = By.xpath("//input[@id='quantity']");

    public WritePracticeIndexPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public WritePracticeIndexPage chooseTranslation () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(translationRadioBy)).click();
        return this;
    }

    public WritePracticeIndexPage chooseSpelling () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(spellingRadioBy)).click();
        return this;
    }

    public WritePracticeIndexPage chooseKanji () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(kanjiRadioBy)).click();
        return this;
    }

    public WritePracticeIndexPage chooseKana () {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(kanaRadioBy)).click();
        return this;
    }

    public WritePracticeIndexPage chooseDefault() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(defaultRadioBy)).click();
        return this;
    }

    public WritePracticeShowPage submit() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return new WritePracticeShowPage(driver, webDriverWait);
    }

    public WritePracticeIndexPage inputNumberOfTasks(String numberOfTasks) {
        WebElement box = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(numberOfTasksBoxBy));
        box.clear();
        box.sendKeys(numberOfTasks);
        return this;
    }

}
