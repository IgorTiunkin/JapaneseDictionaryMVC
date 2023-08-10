package com.phantom.japanese_dictionary_mvc.finders;

import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinderFactory {
    private final NoteService noteService;

    @Autowired
    public FinderFactory(NoteService noteService) {
        this.noteService = noteService;
    }

    public Finder getInstance(String wordToFind) {
        if (wordToFind.matches("[A-Za-z]+")) return new RomajiWordFinder(noteService);
        if (wordToFind.matches(".*\\p{script=Han}+.*")) return new KanjiWordFinder(noteService);
        if (wordToFind.matches(".*\\p{script=Hiragana}+.*") ||
                wordToFind.matches(".*\\p{script=Katakana}+.*")) {
            return new KanaWordFinder(noteService);
        }
        return new RussianWordFinder(noteService);

    }
}
