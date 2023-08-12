package com.phantom.japanese_dictionary_mvc;

import com.phantom.japanese_dictionary_mvc.finders.dictionary.*;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.models.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.module.FindException;
import java.util.Random;

@SpringBootTest
class JapaneseDictionaryMvcApplicationTests {
    private final WordFinderFactory wordFinderFactory;
    private final GrammarFinderFactory grammarFinderFactory;
    private Random random = new Random();

    @Autowired
    JapaneseDictionaryMvcApplicationTests(WordFinderFactory wordFinderFactory, GrammarFinderFactory grammarFinderFactory) {
        this.wordFinderFactory = wordFinderFactory;
        this.grammarFinderFactory = grammarFinderFactory;
    }


    @Test
    void contextLoads() {
    }

    @Test
    public void whenRussian_thenRussianWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('а'+random.nextInt(33)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof RussianWordFinder);
    }

    @Test
    public void whenEnglish_thenEnglishWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('a'+random.nextInt(26)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof RomajiWordFinder);
    }
    @Test
    public void whenKana_thenKanaWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++){
            stringBuilder.append((char)('\u3040'+ random.nextInt(20)));
            stringBuilder.append((char)('\u30a0'+ random.nextInt(20)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof KanaWordFinder);
    }


    @Test
    public void whenKanji_thenKanjiWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++){
            stringBuilder.append((char)('\u4e00'+ random.nextInt(100)));
        }
        stringBuilder.append("漢字日本語文字言語");
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof KanjiWordFinder);
    }

    @Test
    public void whenEnglish_thenEnglishGrammarFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('a'+random.nextInt(26)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        Assertions.assertTrue(grammarFinder instanceof RomajiGrammarFinder);
    }

    @Test
    public void whenKana_thenKanaGrammarFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++){
            stringBuilder.append((char)('\u3040'+ random.nextInt(20)));
            stringBuilder.append((char)('\u30a0'+ random.nextInt(20)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        Assertions.assertTrue(grammarFinder instanceof KanaGrammarFinder);
    }

}
