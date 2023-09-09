package com.phantom.japanese_dictionary_mvc.seleniumTests;

import com.phantom.japanese_dictionary_mvc.pageObject.LoginPage;
import com.phantom.japanese_dictionary_mvc.pageObject.QuizShowPage;
import com.phantom.japanese_dictionary_mvc.pageObject.WelcomePage;
import com.phantom.japanese_dictionary_mvc.pageObject.WritePracticeShowPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Random;

@ActiveProfiles("test")
public abstract class JapaneseDictionarySeleniumTest {
    private WebDriver driver;
    WebDriverWait wait;
    private final String TEST_URL  = "http://localhost:8080/";
    //private final String TEST_URL  = "http://89.223.122.4:8080/";
    private final String NUMBER_OF_WRITE_TESTS = "5";

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

    public void whenLogout_thenAuthPage() {
        loginUser().logout();
        Assertions.assertEquals("Auth", driver.getTitle());
    }

    public void whenChooseTranslationAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseTranslation().inputWord("болезнь").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenChooseSpellingAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseSpelling().inputWord("yamai").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenChooseKanjiAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKanji().inputWord("病").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenChooseKanaAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKana().inputWord("やまい").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenChooseDefaultAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKana().chooseDefault().inputWord("болезнь").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenChooseTranslationFullTextAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseTranslation().chooseFullMatch().inputWord("болезнь").submit();
        Assertions.assertEquals("Multi dictionary result", driver.getTitle());
    }

    public void whenInputTextAndSubmit_thenGrammarShow() {
        loginUser().goToGrammar().inputText("bakari").submit();
        Assertions.assertEquals("Grammar show", driver.getTitle());
    }

    public void whenChooseKanaAndInputInWrite_thenWriteShowUnhiddenKana() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseKana().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        Assertions.assertEquals("word", driver.findElement(showPage.getKanaRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getRomadjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getTranslationRowBy()).getAttribute("class"));
    }

    public void whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseKanji().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanaRowBy()).getAttribute("class"));
        Assertions.assertEquals("word", driver.findElement(showPage.getKanjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getRomadjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getTranslationRowBy()).getAttribute("class"));
    }

    public void whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseSpelling().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanaRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("word", driver.findElement(showPage.getRomadjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getTranslationRowBy()).getAttribute("class"));
    }

    public void whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseTranslation().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanaRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getKanjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("wordhidden", driver.findElement(showPage.getRomadjiRowBy()).getAttribute("class"));
        Assertions.assertEquals("word", driver.findElement(showPage.getTranslationRowBy()).getAttribute("class"));
    }

    public void whenTooManyTasksInput_thenWritePracticeIndex() {
        loginUser().goToWriteTest().inputNumberOfTasks("22").submit();
        Assertions.assertEquals("Write Practice", driver.getTitle());
    }

    public void whenGiveQuizTasks_thenGetRightNumberOfTasks() {
        Random random = new Random();
        int testNumberOfTasks = random.nextInt(20)+1;
        int testNumberOfOptions = random.nextInt(8)+2;
        QuizShowPage quizShowPage = loginUser().goToQuiz()
                .inputNumberOfTasks(String.valueOf(testNumberOfTasks))
                .inputNumberOfOptions(String.valueOf(testNumberOfOptions))
                .submit();
        int realNumberOfTasks = driver.findElements(quizShowPage.getQuizTaskBy()).size();
        int realNumberOfOptions = driver.findElements(quizShowPage.getQuizOptionBy()).size();
        Assertions.assertEquals(testNumberOfTasks, realNumberOfTasks);
        Assertions.assertEquals(testNumberOfTasks*testNumberOfOptions, realNumberOfOptions);
    }

    @AfterEach
    public void quit () throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
