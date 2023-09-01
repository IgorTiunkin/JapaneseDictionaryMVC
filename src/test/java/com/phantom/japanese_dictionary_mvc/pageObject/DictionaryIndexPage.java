package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DictionaryIndexPage extends BasePage{

    private By translationRadioBy = By.xpath("//input[@value='TRANSLATION']");
    private By spellingRadioBy = By.xpath("//input[@value='SPELLING']");
    private By kanjiRadioBy = By.xpath("//input[@value='KANJI']");
    private By kanaRadioBy = By.xpath("//input[@value='KANA']");
    private By defaultRadioBy = By.xpath("//input[@value='DEFAULT']");
    private By onlyFullMatchCheckboxBy = By.xpath("//input[(@id='full') and (@name='onlyFullMatch')]");
    private By submitButtonBy = By.xpath("//input[(@type='submit') and (@value='Поиск')]");
    private By wordBoxBy = By.xpath("//input[@id='word']");

    public DictionaryIndexPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public DictionaryIndexPage chooseTranslation() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(translationRadioBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage chooseSpelling() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(spellingRadioBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage chooseKanji() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(kanjiRadioBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage chooseKana() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(kanaRadioBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage chooseDefault() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(defaultRadioBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage chooseFullMatch() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(onlyFullMatchCheckboxBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage submit() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(submitButtonBy)).click();
        return new DictionaryIndexPage(driver, webDriverWait);
    }

    public DictionaryIndexPage inputWord(String wordToFind) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(wordBoxBy)).sendKeys(wordToFind);
        return new DictionaryIndexPage(driver, webDriverWait);
    }

}
