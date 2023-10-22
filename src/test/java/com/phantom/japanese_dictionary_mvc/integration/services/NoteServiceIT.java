package com.phantom.japanese_dictionary_mvc.integration.services;


import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class NoteServiceIT extends BaseIT {

    private final NoteService noteService;

    private final Note TEST_NOTE_YAMAI = Note.builder()
            .id(1).translation("болезнь").romadji("yamai").kanji("病").hiragana("やまい")
            .build();

    @Autowired
    public NoteServiceIT(NoteService noteService) {
        this.noteService = noteService;
    }

    @Test
    public void whenRussianKofe_then4Partial() {
        String wordToFind = "кофе";
        List<Note> noteList = noteService.findFragmentByRussianText(wordToFind);
        assertEquals(4, noteList.size());
        for (Note note : noteList) {
            assertTrue(note.getTranslation().contains(wordToFind));
        }
    }

    @Test
    public void whenEnglishKofe_then4Partial() {
        String wordToFind = "koohii";
        List<Note> noteList = noteService.findFragmentByEnglishText(wordToFind);
        assertEquals(4, noteList.size());
        for (Note note : noteList) {
            assertTrue(note.getRomadji().contains(wordToFind));
        }
    }

    @Test
    public void whenKanaKofe_then4Partial() {
        String wordToFind = "コーヒー";
        List<Note> noteList = noteService.findFragmentByKanaText(wordToFind);
        assertEquals(4, noteList.size());
        for (Note note : noteList) {
            assertTrue(note.getHiragana().contains(wordToFind));
        }
    }

    @Test
    public void whenKanjiOkawari_then1Partial() {
        String wordToFind = "お代わり";
        List<Note> noteList = noteService.findFragmentByKanjiText(wordToFind);
        assertEquals(1, noteList.size());
        for (Note note : noteList) {
            assertTrue(note.getKanji().contains(wordToFind));
        }
    }

    @Test
    public void whenSave_thenTrue() {
        boolean saveNote = noteService.saveNote(TEST_NOTE_YAMAI);
        assertTrue(saveNote);
    }

    @Test
    public void when3_then3Notes() {
        int numberOfVariants = 3;
        List<Note> randomVariants = noteService.getRandomVariants(numberOfVariants);
        assertEquals(numberOfVariants, randomVariants.size());
    }



}
