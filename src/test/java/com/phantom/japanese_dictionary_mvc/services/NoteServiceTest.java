package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.repositories.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class NoteServiceTest {

    private final Note TEST_NOTE_YAMAI = new Note(1, "yamai", "病", "やまい","болезнь");
    private final Note TEST_NOTE_JOUDAN = new Note(1, "joudan", "冗談", "じょうだん","шутка");


    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;


    @Test
    public void whenRussianText_thenSize2() {
        List <Note> testList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        doReturn(testList).when(noteRepository).findAllByTranslationContains(any());
        assertEquals(2, noteService.findFragmentByRussianText(TEST_NOTE_YAMAI.getTranslation()).size());
    }

    @Test
    public void whenRomajiText_thenSize1() {
        List <Note> testList = List.of(TEST_NOTE_YAMAI);
        doReturn(testList).when(noteRepository).findAllByRomadjiContains(any());
        assertEquals(1, noteService.findFragmentByEnglishText(TEST_NOTE_YAMAI.getRomadji()).size());
    }

    @Test
    public void whenKanaText_thenSize2() {
        List <Note> testList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        doReturn(testList).when(noteRepository).findAllByHiraganaContains(any());
        assertEquals(2, noteService.findFragmentByKanaText(TEST_NOTE_YAMAI.getHiragana()).size());
    }

    @Test
    public void whenKanjiText_thenSize2() {
        List <Note> testList = List.of(TEST_NOTE_YAMAI);
        doReturn(testList).when(noteRepository).findAllByKanjiContains(any());
        assertEquals(1, noteService.findFragmentByKanjiText(TEST_NOTE_YAMAI.getKanji()).size());
    }

    @Test
    public void whenRandom_thenSize2() {
        List <Note> testList = List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN);
        doReturn(testList).when(noteRepository).findAllByIdIn(any());
        doReturn(1L).when(noteRepository).count();
        assertEquals(2, noteService.getRandomVariants(1).size());
    }

    @Test
    public void whenSave_thenNothing(){
        doReturn(TEST_NOTE_YAMAI).when(noteRepository).save(TEST_NOTE_YAMAI);
        assertTrue(noteService.saveNote(TEST_NOTE_YAMAI));
    }




}