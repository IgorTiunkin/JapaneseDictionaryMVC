package com.phantom.japanese_dictionary_mvc.pageObject;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class QuizShowPage extends BasePage{

    private By quizTaskBy = By.xpath("//ul[@id='question']");
    private By quizOptionBy = By.xpath("//input[@type='radio']");

    public QuizShowPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }
}
