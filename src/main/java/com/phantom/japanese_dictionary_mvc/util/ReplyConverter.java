package com.phantom.japanese_dictionary_mvc.util;


import com.phantom.japanese_dictionary_mvc.finders.Finder;
import com.phantom.japanese_dictionary_mvc.finders.FinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.Request;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyConverter {

    private final FinderFactory finderFactory;

    public ReplyConverter(FinderFactory finderFactory) {
        this.finderFactory = finderFactory;
    }

    public List <Note> getFullReplies(Request request) { // take input message - return all replies

        Finder finder = finderFactory.getInstance(request); //choose finder
        List<Note> mixSearchResult = finder.getNotesFromRepository(request.getWord()); //get mixed (full+partial) result

        List <Note> fullMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (finder.checkFullMatch(request.getWord(), note)) { //check full match
                    fullMatch.add(note);
                }
            }
        return fullMatch;
    }

    public List <Note> getPartialReplies(Request request) {

        Finder finder = finderFactory.getInstance(request); //choose finder
        List<Note> mixSearchResult = finder.getNotesFromRepository(request.getWord()); //get mixed (full+partial) result

        List <Note> partialMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (!finder.checkFullMatch(request.getWord(), note)) { //check partial match
                partialMatch.add(note);
            }
        }
        return partialMatch;
    }


}
