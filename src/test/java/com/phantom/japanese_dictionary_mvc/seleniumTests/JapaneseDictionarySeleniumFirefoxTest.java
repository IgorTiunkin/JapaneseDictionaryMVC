package com.phantom.japanese_dictionary_mvc.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JapaneseDictionarySeleniumFirefoxTest extends JapaneseDictionarySeleniumTest {

    public JapaneseDictionarySeleniumFirefoxTest() {
        super(new FirefoxDriver());
    }

    @Test
    public void whenClickWithDefault_thenSuccessfulLogin_Firefox () {
        whenClickWithDefault_thenSuccessfulLogin();
    }

    @Test
    public void whenValidUser_thenSuccessfulLogin_Firefox () {
        whenValidUser_thenSuccessfulLogin();
    }

    @Test
    public void whenInvalidUser_thenLoginPage_Firefox () {
        whenInvalidUser_thenLoginPage();
    }

    @Test
    public void whenInvalidPassword_thenLoginPage_Firefox () {
        whenInvalidPassword_thenLoginPage();
    }

    @Test
    public void whenDictionaryLink_thenDictionaryPage_Firefox() {
        whenDictionaryLink_thenDictionaryPage();
    }

    @Test
    public void whenGrammarLink_thenGrammarPage_Firefox() {
        whenGrammarLink_thenGrammarPage();
    }

    @Test
    public void whenWriteTestLink_thenWriteTestPage_Firefox() {
        whenWriteTestLink_thenWriteTestPage();
    }

    @Test
    public void whenQuizLink_thenQuizPage_Firefox() {
        whenQuizLink_thenQuizPage();
    }

    @Test
    public void whenLogout_thenAuthPage_Firefox() {
        whenLogout_thenAuthPage();
    }

    @Test
    public void whenChooseTranslationAndInputText_thenDictionaryShow_Firefox() {
        whenChooseTranslationAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseSpellingAndInputText_thenDictionaryShow_Firefox() {
        whenChooseSpellingAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanjiAndInputText_thenDictionaryShow_Firefox() {
        whenChooseKanjiAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanaAndInputText_thenDictionaryShow_Firefox() {
        whenChooseKanaAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseDefaultAndInputText_thenDictionaryShow_Firefox() {
        whenChooseDefaultAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseTranslationFullTextAndInputText_thenDictionaryShow_Firefox() {
        whenChooseTranslationFullTextAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenInputTextAndSubmit_thenGrammarShow_Firefox() {
        whenInputTextAndSubmit_thenGrammarShow();
    }

    @Test
    public void whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow_Firefox() {
        whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow();
    }

    @Test
    public void whenChooseKanaAndInputInWrite_thenWriteShowHiddenKana_Firefox() {
        whenChooseKanaAndInputInWrite_thenWriteShowUnhiddenKana();
    }

    @Test
    public void whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji_Firefox() {
        whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji();
    }

    @Test
    public void whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji_Firefox() {
        whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji();
    }

    @Test
    public void whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation_Firefox() {
        whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation();
    }

    @Test
    public void whenTooManyTasksInput_thenWritePracticeIndex_Firefox() {
        whenTooManyTasksInput_thenWritePracticeIndex();
    }

    @Test
    public void whenGiveQuizTasks_thenGetRightNumberOfTasks_Firefox() {
        whenGiveQuizTasks_thenGetRightNumberOfTasks();
    }

}
