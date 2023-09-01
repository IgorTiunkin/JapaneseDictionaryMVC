package com.phantom.japanese_dictionary_mvc;

import com.phantom.japanese_dictionary_mvc.pageObject.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class JapaneseDictionarySeleniumTest {
    private static WebDriver driver;
    private final String TEST_URL  = "http://localhost:8080/";

    @Test
    public void whenClickWithDefault_thenSuccessfulLogin () {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2000));
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginDefaultUser();
        Assertions.assertEquals("Welcome", driver.getTitle());
    }

    @Test
    public void whenValidUser_thenSuccessfulLogin () {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2000));
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user");
        Assertions.assertEquals("Welcome", driver.getTitle());

    }

    @Test
    public void whenInvalidUser_thenLoginPage () {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2000));
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user1", "user");
        Assertions.assertEquals("Auth", driver.getTitle());
    }

    @Test
    public void whenInvalidPassword_thenLoginPage () {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2000));
        driver.get(TEST_URL + "auth/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user1");
        Assertions.assertEquals("Auth", driver.getTitle());
    }

    @AfterEach
    public void quit () {
        driver.quit();
    }
}
