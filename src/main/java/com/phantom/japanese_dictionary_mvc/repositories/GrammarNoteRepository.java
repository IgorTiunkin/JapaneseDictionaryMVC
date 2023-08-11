package com.phantom.japanese_dictionary_mvc.repositories;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrammarNoteRepository extends JpaRepository<GrammarNote, Integer> {
    List <GrammarNote> findAllByRuleContains (String text);

    List <GrammarNote> findAllByExampleContains (String text);


}
