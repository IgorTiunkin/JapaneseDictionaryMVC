package com.phantom.japanese_dictionary_mvc.repositories;

import com.phantom.japanese_dictionary_mvc.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findAllByTranslationContains(String text);

    List<Note> findAllByRomadjiContains(String text);

    List<Note> findAllByHiraganaContains(String text);

    List <Note> findAllByKanjiContains(String text);

    List <Note> findAllByIdIn(Set<Integer> indices);
}
