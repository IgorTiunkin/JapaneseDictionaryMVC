package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findFragmentByRussianText(String text) {
        return noteRepository.findAllByTranslationContains(text);
    }

    public List<Note> findFragmentByEnglishText(String text) {
        return noteRepository.findAllByRomadjiContains(text);
    }

    public List <Note> findFragmentByKanaText(String text) {
        return noteRepository.findAllByHiraganaContains(text);
    }

    public List <Note> findFragmentByKanjiText(String text) {
        return noteRepository.findAllByKanjiContains(text);
    }

    @Transactional
    public boolean saveNote (Note note) {
        noteRepository.save(note);
        return true;
    }

    public List <Note> getRandomVariants(int quantity) {
        Random random = new Random();
        Set <Integer> indicesForVariants = new HashSet<>();
        int count = (int) noteRepository.count();
        quantity = Math.min(quantity, count);
        while (indicesForVariants.size()<quantity) { //method need ids start from 1 and continious id
            indicesForVariants.add(1+random.nextInt(count));
        }
        return noteRepository.findAllByIdIn(indicesForVariants);
    }


}
