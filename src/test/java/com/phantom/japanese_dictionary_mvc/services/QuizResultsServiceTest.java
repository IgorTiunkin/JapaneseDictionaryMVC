package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.repositories.QuizResultsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class QuizResultsServiceTest {

    private final QuizResult TEST_QUIZ_RESULT = new QuizResult(1, null, 5, 10, null, null);

    @Mock
    private QuizResultsRepository quizResultsRepository;

    @InjectMocks
    private QuizResultsService quizResultsService;


    @Test
    void whenGetQuizResultsByUser_thenId1() {
        doReturn(List.of(TEST_QUIZ_RESULT)).when(quizResultsRepository).findAllByUser(any());
        assertEquals(1, quizResultsService.getQuizResultsByUser(null).get(0).getQuizResultId());
    }

    @Test
    void whenSaveQuizResult_thenTrue() {
        doReturn(TEST_QUIZ_RESULT).when(quizResultsRepository).save(TEST_QUIZ_RESULT);
        assertTrue(quizResultsService.saveQuizResult(TEST_QUIZ_RESULT));
    }
}