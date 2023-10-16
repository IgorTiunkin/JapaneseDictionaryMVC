package com.phantom.japanese_dictionary_mvc.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class LanguageDefinerTest {

    @Autowired
    private LanguageDefiner languageDefiner;

    @Test
    public void whenTest_thenEnglish() {
        assertEquals(LanguageType.ENGLISH, languageDefiner.defineLanguage("test"));
    }

    @Test
    public void whenТест_thenRussian() {
        assertEquals(LanguageType.RUSSIAN, languageDefiner.defineLanguage("тест"));
    }

    @Test
    public void whenKana_thenKana() {
        assertEquals(LanguageType.KANA, languageDefiner.defineLanguage("じょうだん"));
    }

    @Test
    public void whenKanji_thenKanji() {
        assertEquals(LanguageType.KANJI, languageDefiner.defineLanguage("上段"));
    }



}