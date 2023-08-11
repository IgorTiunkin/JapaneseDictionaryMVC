package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.repositories.GrammarNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<GrammarNote> findByEnglishFragment (String wordToFind) {
        Set<GrammarNote> grammarNotes = new HashSet<>();
        grammarNotes.addAll(grammarNoteRepository.findAllByExampleContains(wordToFind));
        grammarNotes.addAll(grammarNoteRepository.findAllByRuleContains(wordToFind));
        return new ArrayList<>(grammarNotes);
    }

    public List<GrammarNote> findByKanaFragment (String wordToFind) {
        return grammarNoteRepository.findAllByRuleContains(wordToFind);
    }
}
