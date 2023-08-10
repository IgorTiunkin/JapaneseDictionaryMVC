package com.phantom.japanese_dictionary_mvc.util;


import com.phantom.japanese_dictionary_mvc.finders.Finder;
import com.phantom.japanese_dictionary_mvc.finders.FinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyConverter {

    private final FinderFactory finderFactory;

    public ReplyConverter(FinderFactory finderFactory) {
        this.finderFactory = finderFactory;
    }

    public List <Note> getReplies(String wordToFind) { // take input message - return all replies

        Finder finder = finderFactory.getInstance(wordToFind); //choose finder
        List<Note> searchResult = finder.getNotesFromRepository(wordToFind); //get mixed (full+partial) result

        return searchResult;

        /*List <String> replies = new ArrayList<>();
        if (searchResult == null || searchResult.isEmpty()) { //if nothing found - return stub reply
            replies.add("Фрагмента \"" + wordToFind + "\" нет в моей базе");
        } else {
            replies.add("Количество упоминаний фрагмента \"" + wordToFind + "\" в моей базе - "  + searchResult.size() +  "\n");
            List <Note> fullMatch = new ArrayList<>(); //two options full/partial match
            List <Note> partialMatch = new ArrayList<>();
            for (Note note : searchResult) {
                if (finder.checkFullMatch(wordToFind, note)) { //check full match
                    fullMatch.add(note);
                } else {
                    partialMatch.add(note);
                }
            }
            if (!fullMatch.isEmpty()) { // if we got full match
                replies.add("Количество полных совпадений - " + fullMatch.size() + "\n");
            }
            if (!partialMatch.isEmpty()) {// if we got partial match
                replies.add("Количество частичных совпадений - " + partialMatch.size() + "\n");
            }
        }
        return replies;*/
    }


}
