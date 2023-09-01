package com.phantom.japanese_dictionary_mvc.seleniumTests;

import com.phantom.japanese_dictionary_mvc.pageObject.LoginPage;
import com.phantom.japanese_dictionary_mvc.pageObject.WelcomePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;


public abstract class JapaneseDictionarySeleniumTest {
    private WebDriver driver;
    WebDriverWait wait;
    private final String TEST_URL  = "http://localhost:8080/";

    public JapaneseDictionarySeleniumTest (WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(2000));
    }


    private WelcomePage loginUser() {
        driver.get(TEST_URL + "auth/login");
        return new LoginPage(driver, wait).loginUser("user", "user");
    }


    public void whenClickWithDefault_thenSuccessfulLogin () {
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginDefaultUser();
        Assertions.assertEquals("Welcome", driver.getTitle());
    }


    public void whenValidUser_thenSuccessfulLogin () {
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user");
        Assertions.assertEquals("Welcome", driver.getTitle());

    }


    public void whenInvalidUser_thenLoginPage () {
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user1", "user");
        Assertions.assertEquals("Auth", driver.getTitle());
    }


    public void whenInvalidPassword_thenLoginPage () {
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user1");
        Assertions.assertEquals("Auth", driver.getTitle());
    }


    public void whenDictionaryLink_thenDictionaryPage() {
        loginUser().goToDictionary();
        Assertions.assertEquals("Base dictionary", driver.getTitle());
    }


    public void whenGrammarLink_thenGrammarPage() {
        loginUser().goToGrammar();
        Assertions.assertEquals("Grammar", driver.getTitle());
    }


    public void whenWriteTestLink_thenWriteTestPage() {
        loginUser().goToWriteTest();
        Assertions.assertEquals("Write Practice", driver.getTitle());
    }


    public void whenQuizLink_thenQuizPage() {
        loginUser().goToQuiz();
        Assertions.assertEquals("Quiz", driver.getTitle());
    }



    @AfterEach
    public void quit () {
        driver.quit();
    }
}
