package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class GrammarDictionaryReplyConverterTest {

    @Mock
    private GrammarFinderFactory grammarFinderFactory;
    @Mock
    private BaseGenericConverter baseGenericConverter;
    @Mock
    private KanaGrammarFinder kanaGrammarFinder;
    @Mock
    private RomajiGrammarFinder romajiGrammarFinder;

    @InjectMocks
    private GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;
    private final int LIMIT_OF_GRAMMAR_NOTES_IN_VIEW = 500;
    private final int GRAMMAR_NOTES_PER_PAGE = 10;

    private final Request ENGLISH_REQUEST = new Request("test", RequestType.SPELLING, false);
    private final Request KANA_REQUEST = new Request("test_kana", RequestType.KANA, false);

    private final GrammarNote TEST_GRAMMAR_NOTE
            = new GrammarNote(1, "Test source", "Test rule",
            "Test explanation", "Test example");
    private final GrammarNote TEST_GRAMMAR_NOTE_BAKARI
            = new GrammarNote(2, "Bakari source", "Bakari rule",
            "Bakari explanation", "Bakari example");
    private final GrammarNoteDTO TEST_GRAMMAR_NOTE_DTO_1 = new GrammarNoteDTO("Test source", "Test rule",
            "Test explanation", "Test example");
    private final GrammarNoteDTO TEST_GRAMMAR_NOTE_DTO_2 = new GrammarNoteDTO("Bakari source", "Bakari rule",
            "Bakari explanation", "Bakari example");


    @Test
    public void whenKanaZero_thenZeroKana() {
        doReturn(kanaGrammarFinder).when(grammarFinderFactory).getInstance(any());
        doReturn(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI)).when(kanaGrammarFinder).getNotesFromRepository(any());
        assertEquals(0, grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(KANA_REQUEST, 0)
                .getIndexOfLastPage());

        doReturn(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI)).when(baseGenericConverter)
                .getNotesToShowForCurrentPage(any(),anyInt(),anyInt(), anyInt());
        doReturn(List.of(TEST_GRAMMAR_NOTE_DTO_1, TEST_GRAMMAR_NOTE_DTO_2)).when(baseGenericConverter)
                .convertNoteToNoteDTO(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI), GrammarNoteDTO.class);
        assertEquals(TEST_GRAMMAR_NOTE_DTO_1.getRule(), grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(KANA_REQUEST, 0)
                .getGrammarNoteDTOS().get(0).getRule());
        assertEquals(2, grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(KANA_REQUEST,0)
        .getMatchCount());


    }



}