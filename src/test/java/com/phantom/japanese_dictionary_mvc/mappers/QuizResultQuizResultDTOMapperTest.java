package com.phantom.japanese_dictionary_mvc.mappers;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.models.FailedQuizTask;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class QuizResultQuizResultDTOMapperTest {

    @Mock
    private FailedQuizTaskToDTOMapper failedQuizTaskToDTOMapper;

    @InjectMocks
    private QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper;

    @Test
    public void whenQuizResult_thenQuizDTO () {
        QuizResult quizResult = new QuizResult();
        quizResult.setQuizResultId(1);
        quizResult.setNumberOfRightAnswers(2);
        quizResult.setNumberOfTasks(5);
        quizResult.setDateOfQuiz(LocalDateTime.of(2023, 9, 5, 16, 55, 15));
        FailedQuizTask failedQuizTask = new FailedQuizTask();
        failedQuizTask.setFailedQuizTaskId(2);
        failedQuizTask.setFailedQuestion("Test");
        List<FailedQuizTask> failedQuizTasks = new ArrayList<>();
        failedQuizTasks.add(failedQuizTask);
        quizResult.setFailedQuizTasks(failedQuizTasks);
        doReturn(new FailedQuizTaskDTO(failedQuizTask.getFailedQuizTaskId(), failedQuizTask.getFailedQuestion()))
                .when(failedQuizTaskToDTOMapper).failedQuizTaskToDTO(any());
        QuizResultDTO quizResultDTO = quizResultQuizResultDTOMapper.quizResultToQuizResultDTO(quizResult);
        assertEquals("05-09-2023 16:55:15", quizResultDTO.getDateOfQuiz());
        assertEquals(1, quizResultDTO.getQuizResultId());
        assertEquals(2, quizResultDTO.getNumberOfRightAnswers());
        assertEquals(5, quizResultDTO.getNumberOfTasks());
        assertEquals(2, quizResultDTO.getFailedQuizTasks().get(0).getFailedQuizTaskId());
        assertEquals("Test", quizResultDTO.getFailedQuizTasks().get(0).getFailedQuestion());


    }

}