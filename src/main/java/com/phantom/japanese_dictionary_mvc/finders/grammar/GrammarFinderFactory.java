package com.phantom.japanese_dictionary_mvc.finders.grammar;

import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrammarFinderFactory {
    private final GrammarNoteService grammarNoteService;

    @Autowired
    public GrammarFinderFactory(GrammarNoteService grammarNoteService) {
        this.grammarNoteService = grammarNoteService;
    }

    public GrammarFinder getInstance(Request request) {
        String wordToFind = request.getWord();
        if (wordToFind.matches(".*\\p{script=Hiragana}+.*") ||
                wordToFind.matches(".*\\p{script=Katakana}+.*")) {
            return new KanaGrammarFinder(grammarNoteService);
        }
        return new RomajiGrammarFinder(grammarNoteService);

    }
}
