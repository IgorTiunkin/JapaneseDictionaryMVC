package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.repositories.GrammarNoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class GrammarNoteServiceTest {

    private final GrammarNote TEST_GRAMMAR_NOTE = GrammarNote.builder()
            .grammarNoteId(1).source("Test source").rule("Test rule").explanation("Test explanation").example("Test example")
            .build();
    private final GrammarNote TEST_GRAMMAR_NOTE_BAKARI =  GrammarNote.builder()
            .grammarNoteId(2).source("Bakari source").rule("Bakari rule").explanation("Bakari explanation").example("Bakari example")
            .build();

    @Mock
    private GrammarNoteRepository grammarNoteRepository;

    @InjectMocks
    private GrammarNoteService grammarNoteService;

    @Test
    public void whenSave_thenNothing(){
        doReturn(TEST_GRAMMAR_NOTE).when(grammarNoteRepository).save(TEST_GRAMMAR_NOTE);
        assertTrue(grammarNoteService.saveGrammarNote(TEST_GRAMMAR_NOTE));
    }

    @Test
    public void whenKana_thenSize2() {
        List <GrammarNote> testList = List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI);
        doReturn(testList).when(grammarNoteRepository).findAllByRuleContains(any());
        assertEquals(2, grammarNoteService.findByKanaFragment(TEST_GRAMMAR_NOTE.getRule()).size());
    }

    @Test
    public void whenEnglish_thenPair() {
        doReturn(List.of(TEST_GRAMMAR_NOTE)).when(grammarNoteRepository).findAllByRuleContains(any());
        doReturn(List.of(TEST_GRAMMAR_NOTE_BAKARI)).when(grammarNoteRepository).findAllByExampleContains(any());
        List <GrammarNote> testResult = grammarNoteService.findByEnglishFragment(TEST_GRAMMAR_NOTE.getRule());
        assertEquals(TEST_GRAMMAR_NOTE.getGrammarNoteId(), testResult.get(0).getGrammarNoteId());
        assertEquals(TEST_GRAMMAR_NOTE_BAKARI.getGrammarNoteId(), testResult.get(1).getGrammarNoteId());
    }

}