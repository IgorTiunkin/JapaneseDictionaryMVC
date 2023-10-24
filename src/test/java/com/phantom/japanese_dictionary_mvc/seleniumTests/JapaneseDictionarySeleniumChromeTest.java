package com.phantom.japanese_dictionary_mvc.seleniumTests;

import com.phantom.japanese_dictionary_mvc.pageObject.QuizShowPage;
import com.phantom.japanese_dictionary_mvc.pageObject.WritePracticeShowPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void whenLogout_thenAuthPage_Chrome() {
        whenLogout_thenAuthPage();
    }

    @Test
    public void whenChooseTranslationAndInputText_thenDictionaryShow_Chrome() {
        whenChooseTranslationAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseSpellingAndInputText_thenDictionaryShow_Chrome() {
        whenChooseSpellingAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanjiAndInputText_thenDictionaryShow_Chrome() {
        whenChooseKanjiAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanaAndInputText_thenDictionaryShow_Chrome() {
        whenChooseKanaAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseDefaultAndInputText_thenDictionaryShow_Chrome() {
        whenChooseDefaultAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseTranslationFullTextAndInputText_thenDictionaryShow_Chrome() {
        whenChooseTranslationFullTextAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenInputTextAndSubmit_thenGrammarShow_Chrome() {
        whenInputTextAndSubmit_thenGrammarShow();
    }

    @Test
    public void whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow_Chrome() {
        whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow();
    }

    @Test
    public void whenChooseKanaAndInputInWrite_thenWriteShowHiddenKana_Chrome() {
        whenChooseKanaAndInputInWrite_thenWriteShowUnhiddenKana();
    }

    @Test
    public void whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji_Chrome() {
        whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji();
    }

    @Test
    public void whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji_Chrome() {
        whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji();
    }

    @Test
    public void whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation_Chrome() {
        whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation();
    }

    @Test
    public void whenTooManyTasksInput_thenWritePracticeIndex_Chrome() {
        whenTooManyTasksInput_thenWritePracticeIndex();
    }

    @Test
    public void whenGiveQuizTasks_thenGetRightNumberOfTasks_Chrome() {
        whenGiveQuizTasks_thenGetRightNumberOfTasks();
    }

}
