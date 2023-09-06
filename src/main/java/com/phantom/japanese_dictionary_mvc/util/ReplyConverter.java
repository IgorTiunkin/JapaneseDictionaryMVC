package com.phantom.japanese_dictionary_mvc.util;


import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyConverter {

    private final WordFinderFactory wordFinderFactory;
    private final ModelMapper modelMapper;

    public ReplyConverter(WordFinderFactory wordFinderFactory, ModelMapper modelMapper) {
        this.wordFinderFactory = wordFinderFactory;
        this.modelMapper = modelMapper;
    }

    public DictionaryReply getDictionaryReply(Request request) {
        DictionaryReply dictionaryReply = new DictionaryReply();

        WordFinder wordFinder = wordFinderFactory.getInstance(request); //choose finder
        String wordToFind = request.getWord().trim().toLowerCase();
        List<Note> mixSearchResult = wordFinder.getNotesFromRepository(wordToFind); //get mixed (full+partial) result


        List <Note> fullMatchNotes = getFullMatch(wordFinder, mixSearchResult, wordToFind);
        List <Note> partialMatchNotest = new ArrayList<>();
        if (!request.isOnlyFullMatch()){
            partialMatchNotest = getPartialMatch(wordFinder, mixSearchResult, wordToFind);
        }

        dictionaryReply.setFullMatchCount(fullMatchNotes.size());
        dictionaryReply.setPartialMatchCount(partialMatchNotest.size());

        dictionaryReply.setFullMatchVisible(true);
        dictionaryReply.setPartialMatchVisible(true);
        List <NoteDTO> fullMatchNotesDTO = convertNoteToNoteDTO(fullMatchNotes);
        List <NoteDTO> partialMatchNotesDTO = convertNoteToNoteDTO(partialMatchNotest);
        dictionaryReply.setFullMatchNoteDTOS(fullMatchNotesDTO);
        dictionaryReply.setPartialMatchNoteDTOS(partialMatchNotesDTO);
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


    private List <NoteDTO> convertNoteToNoteDTO(List <Note> notes) {
        List <NoteDTO> noteDTOS = new ArrayList<>();
        for (Note note: notes) noteDTOS.add(modelMapper.map(note, NoteDTO.class));
        return noteDTOS;
    }


}
