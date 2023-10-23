package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.models.Note;
import org.junit.jupiter.api.Nested;
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

    private final Note TEST_NOTE_YAMAI = Note.builder()
            .id(1).translation("болезнь").romadji("yamai").kanji("病").hiragana("やまい")
            .build();
    private final Note TEST_NOTE_JOUDAN = Note.builder()
            .id(2).translation("шутка").romadji("joudan").kanji("冗談").hiragana("じょうだん")
            .build();

    private final GrammarNote TEST_GRAMMAR_NOTE = GrammarNote.builder()
            .grammarNoteId(1).source("Test source").rule("Test rule").explanation("Test explanation").example("Test example")
            .build();
    private final GrammarNote TEST_GRAMMAR_NOTE_BAKARI =  GrammarNote.builder()
            .grammarNoteId(2).source("Bakari source").rule("Bakari rule").explanation("Bakari explanation").example("Bakari example")
            .build();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BaseGenericNoteConverter baseGenericNoteConverter;

    @Nested
    class PreparationOfPage {
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
    }



    @Nested
    class DTO_Converter {
        @Test
        public void whenNote_thenNoteDto() {
            doReturn(new NoteDTO()).when(modelMapper).map(any(), any());
            List <Note> noteList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
            List<NoteDTO> noteDTOS = baseGenericNoteConverter.convertNoteToNoteDTO(noteList, NoteDTO.class);
            assertEquals(NoteDTO.class, noteDTOS.get(0).getClass());
            assertEquals(2, noteDTOS.size());
        }

        @Test
        public void whenGrammarNote_thenGrammarNoteDto() {
            doReturn(new GrammarNoteDTO()).when(modelMapper).map(any(), any());
            List <GrammarNote> noteList = List.of(TEST_GRAMMAR_NOTE, TEST_GRAMMAR_NOTE_BAKARI);
            List<GrammarNoteDTO> grammarNoteDTOS = baseGenericNoteConverter.convertNoteToNoteDTO(noteList, GrammarNoteDTO.class);
            assertEquals(GrammarNoteDTO.class, grammarNoteDTOS.get(0).getClass());
            assertEquals(2, grammarNoteDTOS.size());
        }
    }



}