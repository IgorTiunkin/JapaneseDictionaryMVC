package com.phantom.japanese_dictionary_mvc.mappers;

import com.phantom.japanese_dictionary_mvc.dto.FailedQuizTaskDTO;
import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.models.FailedQuizTask;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime dateOfQuiz = quizResult.getDateOfQuiz();
        String formattedDateOfQuiz = "Дата неизвестна";
        if (dateOfQuiz !=null) {
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            formattedDateOfQuiz = dateOfQuiz.format(formatter);
        }
        quizResultDTO.setDateOfQuiz(formattedDateOfQuiz);
        List<FailedQuizTaskDTO> failedQuizTaskDTOList = new ArrayList<>();
        for (FailedQuizTask failedQuizTask : quizResult.getFailedQuizTasks()) {
            failedQuizTaskDTOList.add(failedQuizTaskToDTOMapper.failedQuizTaskToDTO(failedQuizTask));
        }
        quizResultDTO.setFailedQuizTasks(failedQuizTaskDTOList);
        return quizResultDTO;
    };

}
