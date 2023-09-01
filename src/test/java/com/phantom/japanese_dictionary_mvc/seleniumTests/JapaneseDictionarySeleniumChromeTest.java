package com.phantom.japanese_dictionary_mvc.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JapaneseDictionarySeleniumChromeTest extends JapaneseDictionarySeleniumTest {

    public JapaneseDictionarySeleniumChromeTest () {
        super(new ChromeDriver());
    }

    @Test
    public void whenClickWithDefault_thenSuccessfulLogin_Chrome () {
        whenClickWithDefault_thenSuccessfulLogin();
    }

    @Test
    public void whenValidUser_thenSuccessfulLogin_Chrome () {
        whenValidUser_thenSuccessfulLogin();
    }

    @Test
    public void whenInvalidUser_thenLoginPage_Chrome () {
        whenInvalidUser_thenLoginPage();
    }

    @Test
    public void whenInvalidPassword_thenLoginPage_Chrome () {
        whenInvalidPassword_thenLoginPage();
    }

    @Test
    public void whenDictionaryLink_thenDictionaryPage_Chrome() {
        whenDictionaryLink_thenDictionaryPage();
    }

    @Test
    public void whenGrammarLink_thenGrammarPage_Chrome() {
        whenGrammarLink_thenGrammarPage();
    }

    @Test
    public void whenWriteTestLink_thenWriteTestPage_Chrome() {
        whenWriteTestLink_thenWriteTestPage();
    }

    @Test
    public void whenQuizLink_thenQuizPage_Chrome() {
        whenQuizLink_thenQuizPage();
    }

}
