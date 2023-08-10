package com.phantom.japanese_dictionary_mvc.finders;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RomajiWordFinder implements Finder{
    private final NoteService noteService;

    @Autowired
    public RomajiWordFinder(NoteService noteService) {
        this.noteService = noteService;
    }
    @Override
    public boolean checkFullMatch(String wordToFind, Note note) {
        return (note.getRomadji().trim().matches(".*\\b"+wordToFind+"\\b.*"));
    }

    @Override
    public List<Note> getNotesFromRepository(String wordToFind) {
        return noteService.findFragmentByEnglishText(wordToFind);
    }
}
