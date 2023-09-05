package com.phantom.japanese_dictionary_mvc.mappers;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.models.FailedQuizTask;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizResultQuizResultDTOMapper {

    private final FailedQuizTaskToDTOMapper failedQuizTaskToDTOMapper;

    public QuizResultQuizResultDTOMapper(FailedQuizTaskToDTOMapper failedQuizTaskToDTOMapper) {
        this.failedQuizTaskToDTOMapper = failedQuizTaskToDTOMapper;
    }


    public QuizResultDTO quizResultToQuizResultDTO (QuizResult quizResult) {
        QuizResultDTO quizResultDTO = new QuizResultDTO();
        quizResultDTO.setQuizResultId(quizResult.getQuizResultId());
        quizResultDTO.setNumberOfTasks(quizResult.getNumberOfTasks());
        quizResultDTO.setNumberOfRightAnswers(quizResult.getNumberOfRightAnswers());
        quizResultDTO.setLocalDateOfQuiz(quizResult.getDateOfQuiz().toLocalDate());
        quizResultDTO.setLocalTimeOfQuiz(quizResult.getDateOfQuiz().toLocalTime());
        List<FailedQuizTaskDTO> failedQuizTaskDTOList = new ArrayList<>();
        for (FailedQuizTask failedQuizTask : quizResult.getFailedQuizTasks()) {
            failedQuizTaskDTOList.add(failedQuizTaskToDTOMapper.failedQuizTaskToDTO(failedQuizTask));
        }
        quizResultDTO.setFailedQuizTasks(failedQuizTaskDTOList);
        return quizResultDTO;
    };

}
