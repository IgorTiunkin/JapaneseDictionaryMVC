package com.phantom.japanese_dictionary_mvc.finders.grammar;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;

import java.util.List;

public class RomajiGrammarFinder implements GrammarFinder{
    private final GrammarNoteService grammarNoteService;

    public RomajiGrammarFinder(GrammarNoteService grammarNoteService) {
        this.grammarNoteService = grammarNoteService;
    }

    @Override
    public List<GrammarNote> getNotesFromRepository(String wordToFind) {
        return grammarNoteService.findByEnglishFragment(wordToFind);
    }
}
