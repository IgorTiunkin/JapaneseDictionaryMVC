package com.phantom.japanese_dictionary_mvc.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public BasePage (WebDriver webDriver, WebDriverWait webDriverWait) {
        this.driver = webDriver;
        this.webDriverWait = webDriverWait;
    }

}
