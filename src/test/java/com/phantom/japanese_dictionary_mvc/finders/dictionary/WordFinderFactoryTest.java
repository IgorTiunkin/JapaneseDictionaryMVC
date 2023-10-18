package com.phantom.japanese_dictionary_mvc.finders.dictionary;

import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.util.LanguageDefiner;
import com.phantom.japanese_dictionary_mvc.util.LanguageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class WordFinderFactoryTest {

    @Mock
    private NoteService noteService;

    @Mock
    private LanguageDefiner languageDefiner;

    @InjectMocks
    private WordFinderFactory wordFinderFactory;

    @Test
    public void whenRussian_thenRussianWordFinder() {
        String russian_test = "test";
        Request request = new Request();
        request.setWord(russian_test);
        doReturn(LanguageType.RUSSIAN).when(languageDefiner).defineLanguage(russian_test);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof RussianWordFinder);
    }

    @Test
    public void whenEnglish_thenEnglishWordFinder() {
        String english_test = "test";
        Request request = new Request();
        request.setWord(english_test);
        doReturn(LanguageType.ENGLISH).when(languageDefiner).defineLanguage(english_test);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof RomajiWordFinder);
    }

    @Test
    public void whenKana_thenKanaWordFinder() {
        String kana_test = "test";
        Request request = new Request();
        request.setWord(kana_test);
        doReturn(LanguageType.KANA).when(languageDefiner).defineLanguage(kana_test);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof KanaWordFinder);
    }


    @Test
    public void whenKanji_thenKanjiWordFinder() {
        String kanji_test = "test";
        Request request = new Request();
        request.setWord(kanji_test);
        doReturn(LanguageType.KANJI).when(languageDefiner).defineLanguage(kanji_test);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof KanjiWordFinder);
    }

    @Test
    public void whenRussianType_thenRussianWordFinder() {
        Request request = new Request();
        request.setRequestType(RequestType.TRANSLATION);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof RussianWordFinder);
    }


    @Test
    public void whenEnglishType_thenEnglishWordFinder() {
        Request request = new Request();
        request.setRequestType(RequestType.SPELLING);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof RomajiWordFinder);
    }

    @Test
    public void whenKanaType_thenKanaWordFinder() {
        Request request = new Request();
        request.setRequestType(RequestType.KANA);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof KanaWordFinder);
    }


    @Test
    public void whenKanjiType_thenKanjiWordFinder() {
        Request request = new Request();
        request.setRequestType(RequestType.KANJI);
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        assertTrue(wordFinder instanceof KanjiWordFinder);
    }
}