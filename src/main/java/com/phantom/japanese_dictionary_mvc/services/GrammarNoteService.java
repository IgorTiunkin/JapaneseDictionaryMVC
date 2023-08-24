package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.repositories.GrammarNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class GrammarNoteService {
    private final GrammarNoteRepository grammarNoteRepository;

    public GrammarNoteService(GrammarNoteRepository grammarNoteRepository) {
        this.grammarNoteRepository = grammarNoteRepository;
    }

    @Transactional
    public void saveGrammarNote(GrammarNote grammarNote) {
        grammarNoteRepository.save(grammarNote);
    }

    public List<GrammarNote> findByEnglishFragment (String wordToFind) { //todo - отделить полный?
        LinkedHashSet <GrammarNote> allGrammarNotes = new LinkedHashSet<>();
        allGrammarNotes.addAll(grammarNoteRepository.findAllByRuleContains(wordToFind));
        //we give priority to grammar notes from rules, only after that add from examples checking for doubles
        allGrammarNotes.addAll(grammarNoteRepository.findAllByExampleContains(wordToFind));
        return new ArrayList<GrammarNote>(allGrammarNotes);
    }

    public List<GrammarNote> findByKanaFragment (String wordToFind) {
        return grammarNoteRepository.findAllByRuleContains(wordToFind);
    }
}
