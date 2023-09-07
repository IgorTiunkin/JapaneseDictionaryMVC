package com.phantom.japanese_dictionary_mvc.util;


import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DictionaryReplyConverter {

    private final WordFinderFactory wordFinderFactory;
    private final BaseGenericConverter baseGenericConverter;
    private final int LIMIT_OF_NOTES_IN_VIEW = 500;
    private final int NOTES_PER_PAGE = 10;

    @Autowired
    public DictionaryReplyConverter(WordFinderFactory wordFinderFactory, BaseGenericConverter baseGenericConverter) {
        this.wordFinderFactory = wordFinderFactory;
        this.baseGenericConverter = baseGenericConverter;
    }

    public DictionaryReply getDictionaryReplyForCurrentPage(Request request, Integer page) {
        DictionaryReply dictionaryReply = new DictionaryReply();

        WordFinder wordFinder = wordFinderFactory.getInstance(request); //choose finder
        String wordToFind = request.getWord().trim().toLowerCase();
        List<Note> mixSearchResult = wordFinder.getNotesFromRepository(wordToFind); //get mixed (full+partial) result


        List <Note> fullMatchNotes = getFullMatch(wordFinder, mixSearchResult, wordToFind);
        List <Note> partialMatchNotes = new ArrayList<>();
        if (!request.isOnlyFullMatch()){
            partialMatchNotes = getPartialMatch(wordFinder, mixSearchResult, wordToFind);
        }

        dictionaryReply.setFullMatchCount(fullMatchNotes.size());
        dictionaryReply.setPartialMatchCount(partialMatchNotes.size());

        int indexOfLastPage = (Math.min(fullMatchNotes.size()+partialMatchNotes.size(), LIMIT_OF_NOTES_IN_VIEW)-1)/NOTES_PER_PAGE;
        dictionaryReply.setIndexOfLastPage(indexOfLastPage);

        fullMatchNotes.addAll(partialMatchNotes);
        List <Note> notesToShow = baseGenericConverter.getNotesToShowForCurrentPage(fullMatchNotes, page, LIMIT_OF_NOTES_IN_VIEW, NOTES_PER_PAGE);
        List <NoteDTO> nodeDTOS = baseGenericConverter.convertNoteToNoteDTO(notesToShow, NoteDTO.class);
        dictionaryReply.setNoteDTOS(nodeDTOS);

        return dictionaryReply;
    }


    public List <Note> getFullMatch(WordFinder wordFinder, List<Note> mixSearchResult, String wordToFind) {
        List <Note> fullMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (wordFinder.checkFullMatch(wordToFind, note)) {
                    fullMatch.add(note);
                }
            }
        return fullMatch;
    }

    public List <Note> getPartialMatch(WordFinder wordFinder, List<Note> mixSearchResult, String wordToFind) {
        List <Note> partialMatch = new ArrayList<>();
        for (Note note : mixSearchResult) {
            if (!wordFinder.checkFullMatch(wordToFind, note)) {
                partialMatch.add(note);
            }
        }
        return partialMatch;
    }

}
