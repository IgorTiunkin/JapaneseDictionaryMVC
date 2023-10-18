package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.models.Note;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class BaseGenericNoteConverterTest {

    private final Note TEST_NOTE_YAMAI = new Note(1, "yamai", "病", "やまい","болезнь");
    private final Note TEST_NOTE_JOUDAN = new Note(1, "joudan", "冗談", "じょうだん","шутка");

    private final GrammarNote TEST_GRAMMAR_NOTE
            = new GrammarNote(1, "Test source", "Test rule",
            "Test explanation", "Test example");
    private final GrammarNote TEST_GRAMMAR_NOTE_BAKARI
            = new GrammarNote(2, "Bakari source", "Bakari rule",
            "Bakari explanation", "Bakari example");

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BaseGenericNoteConverter baseGenericNoteConverter;

    @Test
    public void whenFiveNotes_thenFiveNoteInView() {
        List<Note> testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList.add(new Note());
        }
        assertEquals(5, baseGenericNoteConverter.getNotesToShowForCurrentPage
                (testList, 0, 500,10).size());

    }

    @Test
    public void whenFifteenNotesAndLimit10_thenTenNoteInView() {
        List<Note> testList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            testList.add(new Note());
        }
        assertEquals(10, baseGenericNoteConverter.getNotesToShowForCurrentPage
                (testList, 0, 500,10).size());

    }

    @Test
    public void whenFifteenNotesAndLimit10AndSecondPage_thenFiveNoteInView() {
        List<Note> testList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            testList.add(new Note());
        }
        assertEquals(5, baseGenericNoteConverter.getNotesToShowForCurrentPage
                (testList, 1, 500,10).size());

    }

    @Test
    public void whenNumberOfNotesOverLimit_thenLimitNoteInView() {
        List<Note> testList = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            testList.add(new Note());
        }
        assertEquals(10, baseGenericNoteConverter.getNotesToShowForCurrentPage
                (testList, 0, 100,10).size());

    }

    @Test
    public void whenNote_thenNoteDto() {
        doReturn(new NoteDTO()).when(modelMapper).map(any(), any());
        List <Note> noteList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        assertEquals(NoteDTO.class, baseGenericNoteConverter.convertNoteToNoteDTO(noteList, NoteDTO.class).get(0).getClass());
        assertEquals(2,baseGenericNoteConverter.convertNoteToNoteDTO(noteList, NoteDTO.class).size());
    }

    @Test
    public void whenGrammarNote_thenGrammarNoteDto() {
        doReturn(new GrammarNoteDTO()).when(modelMapper).map(any(), any());
        List <GrammarNote> noteList = List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI);
        assertEquals(GrammarNoteDTO.class, baseGenericNoteConverter.convertNoteToNoteDTO(noteList, GrammarNoteDTO.class).get(0).getClass());
        assertEquals(2,baseGenericNoteConverter.convertNoteToNoteDTO(noteList, GrammarNoteDTO.class).size());
    }

}