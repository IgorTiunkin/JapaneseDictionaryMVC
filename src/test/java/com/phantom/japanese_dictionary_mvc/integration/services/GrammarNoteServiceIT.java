package com.phantom.japanese_dictionary_mvc.integration.services;

import com.phantom.japanese_dictionary_mvc.integration.BaseServiceIT;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrammarNoteServiceIT extends BaseServiceIT {

    private final GrammarNoteService grammarNoteService;

    private final GrammarNote TEST_GRAMMAR_NOTE
            = new GrammarNote(1, "Test source", "Test rule",
            "Test explanation", "Test example");

    @Autowired
    public GrammarNoteServiceIT(GrammarNoteService grammarNoteService) {
        this.grammarNoteService = grammarNoteService;
    }

    @Test
    public void whenToShite_then3Partial() {
        String wordToFind = "to shite";
        List<GrammarNote> grammarNotes = grammarNoteService.findByEnglishFragment(wordToFind);
        assertEquals(3, grammarNotes.size());
        for (GrammarNote note : grammarNotes) {
            assertTrue(note.getRule().contains(wordToFind)||note.getExample().contains(wordToFind));
        }
        assertTrue(grammarNotes.get(0).getRule().contains(wordToFind));
        assertTrue(grammarNotes.get(2).getExample().contains(wordToFind)
                && !grammarNotes.get(2).getRule().contains(wordToFind));
    }

    @Test
    public void whenSave_thenTrue() {
        boolean saveNote = grammarNoteService.saveGrammarNote(TEST_GRAMMAR_NOTE);
        assertTrue(saveNote);
    }
}
