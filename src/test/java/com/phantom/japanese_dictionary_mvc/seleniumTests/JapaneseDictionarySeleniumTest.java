package com.phantom.japanese_dictionary_mvc.seleniumTests;

import com.phantom.japanese_dictionary_mvc.pageObject.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public abstract class JapaneseDictionarySeleniumTest {
    private WebDriver driver;
    WebDriverWait wait;
    private final String TEST_URL  = "http://localhost:8080/";
    //private final String TEST_URL  = "http://89.223.122.4:8080/";
    private final String PATH_TO_LOGIN = TEST_URL +"auth/login";
    private final String WELCOME_TITLE = "Welcome";
    private final String AUTH_TITLE = "Auth";
    private final String BASE_DICTIONARY_TITLE = "Base dictionary";
    private final String GRAMMAR_TITLE = "Grammar";
    private final String WRITE_PRACTICE_TITLE = "Write Practice";
    private final String QUIZ_TITLE = "Quiz";
    private final String MULTI_DICTIONARY_RESULT_TITLE = "Multi dictionary result";
    private final String GRAMMAR_SHOW_TITLE = "Grammar show";

    private final String NUMBER_OF_WRITE_TESTS = "5";

    private final String TRANSLATION_WORD_TO_FIND = "болезнь";
    private final String SPELLING_WORD_TO_FIND = "yamai";
    private final String KANJI_WORD_TO_FIND = "病";
    private final String KANA_WORD_TO_FIND = "やまい";
    private final String GRAMMAR_WORD_TO_FIND = "bakari";

    private final String WRITE_PRACTICE_OPEN_CLASS = "word";
    private final String WRITE_PRACTICE_CLOSE_CLASS = "wordhidden";
    private final String WRITE_PRACTICE_CLASS = "class";


    public JapaneseDictionarySeleniumTest (WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(2000));
    }


    private WelcomePage loginUser() {
        driver.get(PATH_TO_LOGIN);
        return new LoginPage(driver, wait).loginUser("user", "user");
    }


    public void whenClickWithDefault_thenSuccessfulLogin () {
        driver.get(PATH_TO_LOGIN);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginDefaultUser();
        assertEquals(WELCOME_TITLE, driver.getTitle());
    }


    public void whenValidUser_thenSuccessfulLogin () {
        driver.get(PATH_TO_LOGIN);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user");
        assertEquals(WELCOME_TITLE, driver.getTitle());
    }


    public void whenInvalidUser_thenLoginPage () {
        driver.get(PATH_TO_LOGIN);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user1", "user");
        assertEquals(AUTH_TITLE, driver.getTitle());
    }


    public void whenInvalidPassword_thenLoginPage () {
        driver.get(PATH_TO_LOGIN);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser("user", "user1");
        assertEquals(AUTH_TITLE, driver.getTitle());
    }


    public void whenDictionaryLink_thenDictionaryPage() {
        loginUser().goToDictionary();
        assertEquals(BASE_DICTIONARY_TITLE, driver.getTitle());
    }


    public void whenGrammarLink_thenGrammarPage() {
        loginUser().goToGrammar();
        assertEquals(GRAMMAR_TITLE, driver.getTitle());
    }


    public void whenWriteTestLink_thenWriteTestPage() {
        loginUser().goToWriteTest();
        assertEquals(WRITE_PRACTICE_TITLE, driver.getTitle());
    }


    public void whenQuizLink_thenQuizPage() {
        loginUser().goToQuiz();
        assertEquals(QUIZ_TITLE, driver.getTitle());
    }

    public void whenLogout_thenAuthPage() {
        loginUser().logout();
        assertEquals(AUTH_TITLE, driver.getTitle());
    }

    public void whenChooseTranslationAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseTranslation().inputWord(TRANSLATION_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenChooseSpellingAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseSpelling().inputWord(SPELLING_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenChooseKanjiAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKanji().inputWord(KANJI_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenChooseKanaAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKana().inputWord(KANA_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenChooseDefaultAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseKana().chooseDefault().inputWord(TRANSLATION_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenChooseTranslationFullTextAndInputText_thenDictionaryShow() {
        loginUser().goToDictionary().chooseTranslation().chooseFullMatch().inputWord(TRANSLATION_WORD_TO_FIND).submit();
        assertEquals(MULTI_DICTIONARY_RESULT_TITLE, driver.getTitle());
    }

    public void whenInputTextAndSubmit_thenGrammarShow() {
        loginUser().goToGrammar().inputText(GRAMMAR_WORD_TO_FIND).submit();
        assertEquals(GRAMMAR_SHOW_TITLE, driver.getTitle());
    }

    //Test for checking navigation - work only for dev
    public void whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow(){
        GrammarIndexPage grammarIndexPage = loginUser().goToGrammar().inputText(GRAMMAR_WORD_TO_FIND).submit().goToPage(2);
        assertEquals(GRAMMAR_SHOW_TITLE, driver.getTitle());
        assertEquals(6, driver.findElements(grammarIndexPage.getTableResultBy()).size());
    }

    public void whenChooseKanaAndInputInWrite_thenWriteShowUnhiddenKana() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseKana().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        assertEquals(WRITE_PRACTICE_OPEN_CLASS, driver.findElement(showPage.getKanaRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getRomadjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getTranslationRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
    }

    public void whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseKanji().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanaRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_OPEN_CLASS, driver.findElement(showPage.getKanjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getRomadjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getTranslationRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
    }

    public void whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseSpelling().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanaRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_OPEN_CLASS, driver.findElement(showPage.getRomadjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getTranslationRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
    }

    public void whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation() {
        WritePracticeShowPage showPage = loginUser().goToWriteTest().chooseTranslation().inputNumberOfTasks(NUMBER_OF_WRITE_TESTS).submit();
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanaRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getKanjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_CLOSE_CLASS, driver.findElement(showPage.getRomadjiRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
        assertEquals(WRITE_PRACTICE_OPEN_CLASS, driver.findElement(showPage.getTranslationRowBy()).getAttribute(WRITE_PRACTICE_CLASS));
    }

    public void whenTooManyTasksInput_thenWritePracticeIndex() {
        loginUser().goToWriteTest().inputNumberOfTasks("22").submit();
        assertEquals(WRITE_PRACTICE_TITLE, driver.getTitle());
    }

    public void whenGiveQuizTasks_thenGetRightNumberOfTasks() {
        int testNumberOfTasks = 10;
        int testNumberOfOptions = 5;
        QuizShowPage quizShowPage = loginUser().goToQuiz()
                .inputNumberOfTasks(String.valueOf(testNumberOfTasks))
                .inputNumberOfOptions(String.valueOf(testNumberOfOptions))
                .submit();
        int realNumberOfTasks = driver.findElements(quizShowPage.getQuizTaskBy()).size();
        int realNumberOfOptions = driver.findElements(quizShowPage.getQuizOptionBy()).size();
        assertEquals(testNumberOfTasks, realNumberOfTasks);
        assertEquals(testNumberOfTasks*testNumberOfOptions, realNumberOfOptions);
    }

    @AfterEach
    public void quit () throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }
}
