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

    private final Note TEST_NOTE_YAMAI = new Note(1, "yamai", "病", "やまい","болезнь");
    private final Note TEST_NOTE_JOUDAN = new Note(2, "joudan", "冗談", "じょうだん","шутка");
    private final Note TEST_NOTE_SENSOU = new Note(3, "sensou", "戦争", "せんそう","войн");
    private final Note TEST_NOTE_KUMO = new Note(4, "kumo", "蜘蛛", "くも","паук");

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