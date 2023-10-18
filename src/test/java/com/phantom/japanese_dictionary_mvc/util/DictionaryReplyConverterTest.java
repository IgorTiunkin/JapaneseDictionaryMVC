package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.RussianWordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinder;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.WordFinderFactory;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class DictionaryReplyConverterTest {

    @Mock
    private WordFinderFactory wordFinderFactory;
    @Mock
    private BaseGenericNoteConverter baseGenericNoteConverter;
    @Mock
    private RussianWordFinder russianWordFinder;

    @InjectMocks
    private DictionaryReplyConverter dictionaryReplyConverter;

    private final Note TEST_NOTE_YAMAI = new Note(1, "yamai", "病", "やまい","болезнь");
    private final Note TEST_NOTE_JOUDAN = new Note(1, "joudan", "冗談", "じょうだん","шутка");
    private final NoteDTO TEST_NOTEDTO_YAMAI = new NoteDTO("yamai", "病", "やまい","болезнь");
    private final NoteDTO TEST_NOTEDTO_JOUDAN = new NoteDTO("joudan", "冗談", "じょうだん","шутка");


    @Test
    public void whenFullRequest_thenFullMatch() {

        Request request = new Request("test", RequestType.TRANSLATION, false);

        doReturn(russianWordFinder).when(wordFinderFactory).getInstance(any());
        List<Note> testList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        doReturn(testList).when(russianWordFinder).getNotesFromRepository(any());

        doReturn(true).when(russianWordFinder).checkFullMatch(request.getWord(), TEST_NOTE_YAMAI);
        doReturn(false).when(russianWordFinder).checkFullMatch(request.getWord(), TEST_NOTE_JOUDAN);

        doReturn(List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN)).when(baseGenericNoteConverter).getNotesToShowForCurrentPage(any(), anyInt(),anyInt(),anyInt());
        doReturn(List.of(TEST_NOTEDTO_YAMAI, TEST_NOTEDTO_JOUDAN)).when(baseGenericNoteConverter).convertNoteToNoteDTO(any(), any());

        DictionaryReply dictionaryReplyForCurrentPage = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request, 0);
        assertEquals(2, dictionaryReplyForCurrentPage.getNoteDTOS().size());
        assertEquals(1, dictionaryReplyForCurrentPage.getFullMatchCount());
        assertEquals(1, dictionaryReplyForCurrentPage.getPartialMatchCount());
        assertEquals(0, dictionaryReplyForCurrentPage.getIndexOfLastPage());

    }

    @Test
    public void whenOnlyFullMatchRequest_thenOnlyFullMatch() {

        Request request = new Request("test", RequestType.TRANSLATION, true);

        doReturn(russianWordFinder).when(wordFinderFactory).getInstance(any());
        List<Note> testList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        doReturn(testList).when(russianWordFinder).getNotesFromRepository(any());

        doReturn(true).when(russianWordFinder).checkFullMatch(request.getWord(), TEST_NOTE_YAMAI);
        doReturn(false).when(russianWordFinder).checkFullMatch(request.getWord(), TEST_NOTE_JOUDAN);

        doReturn(List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN)).when(baseGenericNoteConverter).getNotesToShowForCurrentPage(any(), anyInt(),anyInt(),anyInt());
        doReturn(List.of(TEST_NOTEDTO_YAMAI, TEST_NOTEDTO_JOUDAN)).when(baseGenericNoteConverter).convertNoteToNoteDTO(any(), any());

        DictionaryReply dictionaryReplyForCurrentPage = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request, 0);
        assertEquals(1, dictionaryReplyForCurrentPage.getFullMatchCount());
        assertEquals(0, dictionaryReplyForCurrentPage.getPartialMatchCount());
        assertEquals(0, dictionaryReplyForCurrentPage.getIndexOfLastPage());

    }
}