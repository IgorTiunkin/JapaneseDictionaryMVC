package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.GrammarRequest;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
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
    private BaseGenericNoteConverter baseGenericNoteConverter;
    @Mock
    private KanaGrammarFinder kanaGrammarFinder;
    @Mock
    private RomajiGrammarFinder romajiGrammarFinder;

    @InjectMocks
    private GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;
    private final int LIMIT_OF_GRAMMAR_NOTES_IN_VIEW = 500;
    private final int GRAMMAR_NOTES_PER_PAGE = 10;

    private final GrammarRequest KANA_REQUEST = new GrammarRequest("test_kana");

    private final GrammarNote TEST_GRAMMAR_NOTE = GrammarNote.builder()
            .grammarNoteId(1).source("Test source").rule("Test rule").explanation("Test explanation").example("Test example")
            .build();
    private final GrammarNote TEST_GRAMMAR_NOTE_BAKARI =  GrammarNote.builder()
            .grammarNoteId(2).source("Bakari source").rule("Bakari rule").explanation("Bakari explanation").example("Bakari example")
            .build();
    private final GrammarNoteDTO TEST_GRAMMAR_NOTE_DTO_1 = GrammarNoteDTO.builder()
            .source("Test source").rule("Test rule").explanation("Test explanation").example("Test example")
            .build();
    private final GrammarNoteDTO TEST_GRAMMAR_NOTE_DTO_2 = GrammarNoteDTO.builder()
            .source("Bakari source").rule("Bakari rule").explanation("Bakari explanation").example("Bakari example")
            .build();


    @Test
    public void whenKanaZero_thenZeroKana() {
        doReturn(kanaGrammarFinder).when(grammarFinderFactory).getInstance(any());
        doReturn(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI)).when(kanaGrammarFinder).getNotesFromRepository(any());
        assertEquals(0, grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(KANA_REQUEST, 0)
                .getIndexOfLastPage());

        doReturn(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI)).when(baseGenericNoteConverter)
                .getNotesToShowForCurrentPage(any(),anyInt(),anyInt(), anyInt());
        doReturn(List.of(TEST_GRAMMAR_NOTE_DTO_1, TEST_GRAMMAR_NOTE_DTO_2)).when(baseGenericNoteConverter)
                .convertNoteToNoteDTO(List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI), GrammarNoteDTO.class);

        GrammarDictionaryReply grammarDictionaryReplyForCurrentPage = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(KANA_REQUEST, 0);
        assertEquals(TEST_GRAMMAR_NOTE_DTO_1.getRule(), grammarDictionaryReplyForCurrentPage
                .getGrammarNoteDTOS().get(0).getRule());
        assertEquals(2, grammarDictionaryReplyForCurrentPage
        .getMatchCount());


    }



}