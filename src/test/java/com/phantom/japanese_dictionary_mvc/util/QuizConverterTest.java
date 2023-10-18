package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class QuizConverterTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private QuizConverter quizConverter;

    private final QuizTask TEST_QUIZ_TASk1 = new QuizTask(1, "q1", "a1", List.of("o1", "o2"));
    private final QuizTask TEST_QUIZ_TASk2 = new QuizTask(2, "q2", "a2", List.of("o3", "o4"));

    @Test
    public void when_then() {
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setNumberOfTasks(2);
        quizRequest.setNumberOfOptions(2);
    }

}