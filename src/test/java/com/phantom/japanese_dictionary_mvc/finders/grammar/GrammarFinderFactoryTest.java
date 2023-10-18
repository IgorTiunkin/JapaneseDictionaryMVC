package com.phantom.japanese_dictionary_mvc.finders.grammar;

import com.phantom.japanese_dictionary_mvc.finders.dictionary.RomajiWordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinder;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import com.phantom.japanese_dictionary_mvc.util.LanguageDefiner;
import com.phantom.japanese_dictionary_mvc.util.LanguageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class GrammarFinderFactoryTest {

    @Mock
    private GrammarNoteService grammarNoteService;

    @Mock
    private LanguageDefiner languageDefiner;

    @InjectMocks
    private GrammarFinderFactory grammarFinderFactory;

    @Test
    public void whenEnglish_thenEnglishGrammarFinder() {
        String english_test = "test";
        Request request = new Request();
        request.setWord(english_test);
        doReturn(LanguageType.ENGLISH).when(languageDefiner).defineLanguage(english_test);
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        assertTrue(grammarFinder instanceof RomajiGrammarFinder);
    }

    @Test
    public void whenKana_thenKanaGrammarFinder() {
        String kana_test = "test";
        Request request = new Request();
        request.setWord(kana_test);
        doReturn(LanguageType.KANA).when(languageDefiner).defineLanguage(kana_test);
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        assertTrue(grammarFinder instanceof KanaGrammarFinder);
    }

}