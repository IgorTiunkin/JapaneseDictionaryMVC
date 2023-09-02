package com.phantom.japanese_dictionary_mvc.pageObject;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class WritePracticeShowPage extends BasePage{

    private By kanjiRowBy = By.xpath("//tr[@id='Kanji']");
    private By kanaRowBy = By.xpath("//tr[@id='Kana']");
    private By translationRowBy = By.xpath("//tr[@id='Translation']");
    private By romadjiRowBy = By.xpath("//tr[@id='Romadji']");


    public WritePracticeShowPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }


}
