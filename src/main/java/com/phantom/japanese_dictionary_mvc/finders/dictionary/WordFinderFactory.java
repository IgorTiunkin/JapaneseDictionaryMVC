package com.phantom.japanese_dictionary_mvc.finders.dictionary;

import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.util.LanguageDefiner;
import com.phantom.japanese_dictionary_mvc.util.LanguageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordFinderFactory {
    private final NoteService noteService;
    private final LanguageDefiner languageDefiner;

    @Autowired
    public WordFinderFactory(NoteService noteService, LanguageDefiner languageDefiner) {
        this.noteService = noteService;
        this.languageDefiner = languageDefiner;
    }

    public WordFinder getInstance(Request request) {
        if (request.getRequestType() == RequestType.KANA) return new KanaWordFinder(noteService);
        if (request.getRequestType() == RequestType.KANJI) return new KanjiWordFinder(noteService);
        if (request.getRequestType() == RequestType.TRANSLATION) return new RussianWordFinder(noteService);
        if (request.getRequestType() == RequestType.SPELLING) return new RomajiWordFinder(noteService);
        String wordToFind = request.getWord();
        if (languageDefiner.defineLanguage(wordToFind) == LanguageType.ENGLISH) {
            return new RomajiWordFinder(noteService);
        }
        if (languageDefiner.defineLanguage(wordToFind) == LanguageType.KANJI) {
            return new KanjiWordFinder(noteService);
        }
        if (languageDefiner.defineLanguage(wordToFind) == LanguageType.KANA) {
            return new KanaWordFinder(noteService);
        }
        return new RussianWordFinder(noteService);

    }
}
