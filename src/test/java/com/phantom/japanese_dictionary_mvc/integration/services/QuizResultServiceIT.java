package com.phantom.japanese_dictionary_mvc.integration.services;

import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizResultServiceIT extends BaseIT {

    private final QuizResultsService quizResultsService;

    private final QuizResult TEST_QUIZ_RESULT = new QuizResult(1, null, 5, 10, null, null);
    private final Person PERSON = new Person(1, "username", "password", "admin",
            new ArrayList<QuizResult>());

    @Autowired
    public QuizResultServiceIT(QuizResultsService quizResultsService) {
        this.quizResultsService = quizResultsService;
    }

    @Test
    public void whenQuizResult_thenSave() {
        boolean saveQuizResult = quizResultsService.saveQuizResult(TEST_QUIZ_RESULT);
        assertTrue(saveQuizResult);
    }

    @Test
    public void whenUser1_then4Results() {
        List<QuizResult> quizResultsByUser = quizResultsService.getQuizResultsByUser(PERSON);
        assertEquals(4, quizResultsByUser.size());
    }

}
