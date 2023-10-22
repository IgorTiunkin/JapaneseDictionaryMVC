package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class QuizConverterTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private QuizConverter quizConverter;

    private final Note TEST_NOTE_YAMAI = Note.builder()
            .id(1).translation("болезнь").romadji("yamai").kanji("病").hiragana("やまい")
            .build();
    private final Note TEST_NOTE_JOUDAN = Note.builder()
            .id(2).translation("шутка").romadji("joudan").kanji("冗談").hiragana("じょうだん")
            .build();
    private final Note TEST_NOTE_SENSOU = Note.builder()
            .id(3).translation("войн").romadji("sensou").kanji("戦争").hiragana("せんそう")
            .build();
    private final Note TEST_NOTE_KUMO = Note.builder()
            .id(4).translation("паук").romadji("kumo").kanji("戦争").hiragana("くも")
            .build();

    @Test
    public void whenTranslation_thenKanji() {
        int numberOfTasks = 2;
        int numberOfOptions = 2;
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setNumberOfTasks(numberOfTasks);
        quizRequest.setNumberOfOptions(numberOfOptions);
        quizRequest.setRequestType(RequestType.TRANSLATION);
        doReturn(List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN, TEST_NOTE_SENSOU, TEST_NOTE_KUMO))
                .when(noteService).getRandomVariants(4);
        List<QuizTask> quizTasks = quizConverter.getQuizTasks(quizRequest);
        assertEquals(numberOfTasks, quizTasks.size());
        assertEquals(numberOfOptions, quizTasks.get(0).getOptions().size());
        assertEquals(TEST_NOTE_YAMAI.getKanji(), quizTasks.get(0).getRightAnswer());
    }

    @Test
    public void whenKanji_thenTranslation() {
        int numberOfTasks = 2;
        int numberOfOptions = 2;
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setNumberOfTasks(numberOfTasks);
        quizRequest.setNumberOfOptions(numberOfOptions);
        quizRequest.setRequestType(RequestType.KANJI);
        doReturn(List.of(TEST_NOTE_YAMAI, TEST_NOTE_JOUDAN, TEST_NOTE_SENSOU, TEST_NOTE_KUMO))
                .when(noteService).getRandomVariants(4);
        List<QuizTask> quizTasks = quizConverter.getQuizTasks(quizRequest);
        assertEquals(numberOfTasks, quizTasks.size());
        assertEquals(numberOfOptions, quizTasks.get(0).getOptions().size());
        assertEquals(TEST_NOTE_YAMAI.getTranslation(), quizTasks.get(0).getRightAnswer());
    }

}