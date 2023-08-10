package com.phantom.japanese_dictionary_mvc.finders;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RussianWordFinder implements Finder{

    private final NoteService noteService;

    @Autowired
    public RussianWordFinder(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public boolean checkFullMatch(String wordToFind, Note note) {
        return note.getTranslation().startsWith(wordToFind + " ") ||
                note.getTranslation().contains(" " + wordToFind + " ") ||
                note.getTranslation().equals(wordToFind) ||
                note.getTranslation().endsWith(" " + wordToFind);
    }

    @Override
    public List<Note> getNotesFromRepository(String wordToFind) {
        return noteService.findFragmentByRussianText(wordToFind);
    }
}
