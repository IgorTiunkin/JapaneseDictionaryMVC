package com.phantom.japanese_dictionary_mvc.seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JapaneseDictionarySeleniumEdgeTest extends JapaneseDictionarySeleniumTest {

    public JapaneseDictionarySeleniumEdgeTest() {
        super(new EdgeDriver());
    }

    @Test
    public void whenClickWithDefault_thenSuccessfulLogin_Edge () {
        whenClickWithDefault_thenSuccessfulLogin();
    }

    @Test
    public void whenValidUser_thenSuccessfulLogin_Edge () {
        whenValidUser_thenSuccessfulLogin();
    }

    @Test
    public void whenInvalidUser_thenLoginPage_Edge () {
        whenInvalidUser_thenLoginPage();
    }

    @Test
    public void whenInvalidPassword_thenLoginPage_Edge () {
        whenInvalidPassword_thenLoginPage();
    }

    @Test
    public void whenDictionaryLink_thenDictionaryPage_Edge() {
        whenDictionaryLink_thenDictionaryPage();
    }

    @Test
    public void whenGrammarLink_thenGrammarPage_Edge() {
        whenGrammarLink_thenGrammarPage();
    }

    @Test
    public void whenWriteTestLink_thenWriteTestPage_Edge() {
        whenWriteTestLink_thenWriteTestPage();
    }

    @Test
    public void whenQuizLink_thenQuizPage_Edge() {
        whenQuizLink_thenQuizPage();
    }

    @Test
    public void whenLogout_thenAuthPage_Edge() {
        whenLogout_thenAuthPage();
    }

    @Test
    public void whenChooseTranslationAndInputText_thenDictionaryShow_Edge() {
        whenChooseTranslationAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseSpellingAndInputText_thenDictionaryShow_Edge() {
        whenChooseSpellingAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanjiAndInputText_thenDictionaryShow_Edge() {
        whenChooseKanjiAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseKanaAndInputText_thenDictionaryShow_Edge() {
        whenChooseKanaAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseDefaultAndInputText_thenDictionaryShow_Edge() {
        whenChooseDefaultAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenChooseTranslationFullTextAndInputText_thenDictionaryShow_Edge() {
        whenChooseTranslationFullTextAndInputText_thenDictionaryShow();
    }

    @Test
    public void whenInputTextAndSubmit_thenGrammarShow_Edge() {
        whenInputTextAndSubmit_thenGrammarShow();
    }

    @Test
    public void whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow_Edge() {
        whenInputTextAndSubmitAndGoToLastPage_thenGrammarShow();
    }

    @Test
    public void whenChooseKanaAndInputInWrite_thenWriteShowHiddenKana_Edge() {
        whenChooseKanaAndInputInWrite_thenWriteShowUnhiddenKana();
    }

    @Test
    public void whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji_Edge() {
        whenChooseKanjiAndInputInWrite_thenWriteShowUnhiddenKanji();
    }

    @Test
    public void whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji_Edge() {
        whenChooseRomajiAndInputInWrite_thenWriteShowUnhiddenRomaji();
    }

    @Test
    public void whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation_Edge() {
        whenChooseTranslationAndInputInWrite_thenWriteShowUnhiddenTranslation();
    }

    @Test
    public void whenTooManyTasksInput_thenWritePracticeIndex_Edge() {
        whenTooManyTasksInput_thenWritePracticeIndex();
    }

    @Test
    public void whenGiveQuizTasks_thenGetRightNumberOfTasks_Edge() {
        whenGiveQuizTasks_thenGetRightNumberOfTasks();
    }

}
