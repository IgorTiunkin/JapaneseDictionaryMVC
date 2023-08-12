package com.phantom.japanese_dictionary_mvc.util;


import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.Request;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyConverter {

    private final WordFinderFactory wordFinderFactory;

    public ReplyConverter(WordFinderFactory wordFinderFactory) {
        this.wordFinderFactory = wordFinderFactory;
    }

    public List <Note> getFullReplies(Request request) { // take input message - return all replies

        WordFinder wordFinder = wordFinderFactory.getInstance(request); //choose finder
        List<Note> mixSearchResult = wordFinder.getNotesFromRepository(request.getWord()); //get mixed (full+partial) result

        List <Note> fullMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (wordFinder.checkFullMatch(request.getWord(), note)) { //check full match
                    fullMatch.add(note);
                }
            }
        return fullMatch;
    }

    public List <Note> getPartialReplies(Request request) {

        WordFinder wordFinder = wordFinderFactory.getInstance(request); //choose finder
        List<Note> mixSearchResult = wordFinder.getNotesFromRepository(request.getWord()); //get mixed (full+partial) result

        List <Note> partialMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (!wordFinder.checkFullMatch(request.getWord(), note)) { //check partial match
                partialMatch.add(note);
            }
        }
        return partialMatch;
    }


}
