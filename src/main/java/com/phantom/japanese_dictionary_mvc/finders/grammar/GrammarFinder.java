package com.phantom.japanese_dictionary_mvc.finders.grammar;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.models.Note;

import java.util.List;

public interface GrammarFinder {
    List<GrammarNote> getNotesFromRepository(String wordToFind);
}
