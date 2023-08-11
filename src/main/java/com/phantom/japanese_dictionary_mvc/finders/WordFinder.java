package com.phantom.japanese_dictionary_mvc.finders;

import com.phantom.japanese_dictionary_mvc.models.Note;

import java.util.List;

public interface WordFinder {
    boolean checkFullMatch(String wordToFind, Note note);
    List<Note> getNotesFromRepository(String wordToFind);
}
