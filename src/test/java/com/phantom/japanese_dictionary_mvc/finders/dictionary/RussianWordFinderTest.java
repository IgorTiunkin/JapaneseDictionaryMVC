package com.phantom.japanese_dictionary_mvc.finders.dictionary;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class RussianWordFinderTest {

    private final NoteService noteService;

    @Autowired
    public RussianWordFinderTest(NoteService noteService) {
        this.noteService = noteService;
    }


    @Test
    public void whenRussianWords_thenCorrectFullMatch () {
        WordFinder wordFinder = new RussianWordFinder(noteService);
        Note noteToTest = new Note();

        noteToTest.setTranslation("тест");
        assertAll( "тест",
                () ->  assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () ->  assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () ->  assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () ->  assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation("тест ");
        assertAll("тест ",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation(" тест");
        assertAll(" тест",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation(" тест ");
        assertAll(" тест ",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation("тест-");
        assertAll("тест-",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation("тест,");
        assertAll("тест,",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation(",тест ");
        assertAll(",тест ",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );

        noteToTest.setTranslation(",тест,");
        assertAll(",тест ",
                () -> assertTrue(wordFinder.checkFullMatch("тест", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тест1", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("тес", noteToTest)),
                () -> assertFalse(wordFinder.checkFullMatch("ест", noteToTest))
        );


    }

}
