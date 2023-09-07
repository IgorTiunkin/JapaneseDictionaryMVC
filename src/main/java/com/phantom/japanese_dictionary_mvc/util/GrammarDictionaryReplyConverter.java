package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrammarDictionaryReplyConverter {

    private final GrammarFinderFactory grammarFinderFactory;
    private final ModelMapper modelMapper;
    private final int LIMIT_OF_GRAMMAR_NOTES_IN_VIEW = 500;
    private final int GRAMMAR_NOTES_PER_PAGE = 10;

    public GrammarDictionaryReplyConverter(GrammarFinderFactory grammarFinderFactory, ModelMapper modelMapper) {
        this.grammarFinderFactory = grammarFinderFactory;
        this.modelMapper = modelMapper;
    }

    public GrammarDictionaryReply getGrammarDictionaryReplyForCurrentPage(Request request, Integer page) {
        GrammarDictionaryReply grammarDictionaryReply = new GrammarDictionaryReply();

        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        List <GrammarNote> notesFromRepository = grammarFinder.getNotesFromRepository(request.getWord().trim().toLowerCase());

        grammarDictionaryReply.setMatchCount(notesFromRepository.size());

        int indexOfLastPage = (Math.min(notesFromRepository.size(), LIMIT_OF_GRAMMAR_NOTES_IN_VIEW)-1)/GRAMMAR_NOTES_PER_PAGE;
        grammarDictionaryReply.setIndexOfLastPage(indexOfLastPage);

        List <GrammarNote> grammarNotesToShow = getGrammarNotesToShowForCurrentPage(notesFromRepository, page);
        List <GrammarNoteDTO> grammarNotesDTOToShow = convertGrammarNoteToGrammarNoteDTO(grammarNotesToShow);
        grammarDictionaryReply.setGrammarNoteDTOS(grammarNotesDTOToShow);

        return grammarDictionaryReply;
    }

    private List<GrammarNote> getGrammarNotesToShowForCurrentPage(List<GrammarNote> notesFromRepository, Integer page) {
        int indexOfLastGrammarNote = Math.min(notesFromRepository.size(), LIMIT_OF_GRAMMAR_NOTES_IN_VIEW);
        Integer currentPage = Math.min(page, notesFromRepository.size()/GRAMMAR_NOTES_PER_PAGE);
        List <GrammarNote> grammarNotesToShowForCurrentPage = new ArrayList<>();
        for (int i = currentPage*GRAMMAR_NOTES_PER_PAGE;
             i < Math.min ((currentPage+1)*GRAMMAR_NOTES_PER_PAGE, indexOfLastGrammarNote); i++) {
            grammarNotesToShowForCurrentPage.add(notesFromRepository.get(i));
        }
        return grammarNotesToShowForCurrentPage;
    }

    private List <GrammarNoteDTO> convertGrammarNoteToGrammarNoteDTO(List <GrammarNote> grammarNotes) {
        List <GrammarNoteDTO> grammarNoteDTO = new ArrayList<>();
        for (GrammarNote grammarNote: grammarNotes) grammarNoteDTO.add(modelMapper.map(grammarNote, GrammarNoteDTO.class));
        return grammarNoteDTO;
    }
}
