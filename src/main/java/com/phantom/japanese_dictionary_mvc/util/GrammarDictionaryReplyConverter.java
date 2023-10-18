package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrammarDictionaryReplyConverter {

    private final GrammarFinderFactory grammarFinderFactory;
    private final BaseGenericNoteConverter baseGenericNoteConverter;
    private final int LIMIT_OF_GRAMMAR_NOTES_IN_VIEW = 500;
    private final int GRAMMAR_NOTES_PER_PAGE = 10;

    public GrammarDictionaryReplyConverter(GrammarFinderFactory grammarFinderFactory,
                                           BaseGenericNoteConverter baseGenericNoteConverter) {
        this.grammarFinderFactory = grammarFinderFactory;
        this.baseGenericNoteConverter = baseGenericNoteConverter;
    }

    public GrammarDictionaryReply getGrammarDictionaryReplyForCurrentPage(Request request, Integer page) {
        GrammarDictionaryReply grammarDictionaryReply = new GrammarDictionaryReply();

        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        String wordToFind = request.getWord().trim().toLowerCase();

        List <GrammarNote> notesFromRepository = grammarFinder.getNotesFromRepository(wordToFind);
        grammarDictionaryReply.setMatchCount(notesFromRepository.size());

        int countOfGrammarNotesToShow = Math.min(notesFromRepository.size(),
                LIMIT_OF_GRAMMAR_NOTES_IN_VIEW);

        //start from 0
        int indexOfLastPage = (countOfGrammarNotesToShow -1)/GRAMMAR_NOTES_PER_PAGE;
        grammarDictionaryReply.setIndexOfLastPage(indexOfLastPage);

        List <GrammarNote> grammarNotesToShow = baseGenericNoteConverter.getNotesToShowForCurrentPage
                (notesFromRepository, page,
                LIMIT_OF_GRAMMAR_NOTES_IN_VIEW, GRAMMAR_NOTES_PER_PAGE);
        List <GrammarNoteDTO> grammarNotesDTOToShow = baseGenericNoteConverter.convertNoteToNoteDTO(grammarNotesToShow,
                GrammarNoteDTO.class);
        grammarDictionaryReply.setGrammarNoteDTOS(grammarNotesDTOToShow);

        return grammarDictionaryReply;
    }

}
