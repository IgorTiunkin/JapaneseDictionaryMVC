package com.phantom.japanese_dictionary_mvc.finders.grammar;

import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import com.phantom.japanese_dictionary_mvc.util.LanguageDefiner;
import com.phantom.japanese_dictionary_mvc.util.LanguageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrammarFinderFactory {
    private final GrammarNoteService grammarNoteService;
    private final LanguageDefiner languageDefiner;

    @Autowired
    public GrammarFinderFactory(GrammarNoteService grammarNoteService, LanguageDefiner languageDefiner) {
        this.grammarNoteService = grammarNoteService;
        this.languageDefiner = languageDefiner;
    }

    public GrammarFinder getInstance(Request request) {
        String wordToFind = request.getWord();
        if (languageDefiner.defineLanguage(wordToFind) == LanguageType.KANA) {
            return new KanaGrammarFinder(grammarNoteService);
        }
        return new RomajiGrammarFinder(grammarNoteService);

    }
}
