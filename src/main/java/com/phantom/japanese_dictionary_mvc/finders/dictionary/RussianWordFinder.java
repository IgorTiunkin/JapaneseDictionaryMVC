package com.phantom.japanese_dictionary_mvc.finders.dictionary;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class RussianWordFinder implements WordFinder {

    private final NoteService noteService;

    @Autowired
    public RussianWordFinder(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public boolean checkFullMatch(String wordToFind, Note note) {
        return note.getTranslation().matches(".*\\b" + wordToFind + "\\b.*");
    }

    @Override
    public List<Note> getNotesFromRepository(String wordToFind) {
        return noteService.findFragmentByRussianText(wordToFind);
    }
}
