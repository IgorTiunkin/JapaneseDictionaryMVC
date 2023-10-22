package com.phantom.japanese_dictionary_mvc.dto;


import com.phantom.japanese_dictionary_mvc.models.FailedQuizTask;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultDTO {

    private int quizResultId;

    private int numberOfRightAnswers;

    private int numberOfTasks;

    private String dateOfQuiz;

    private List<FailedQuizTaskDTO> failedQuizTasks;

}
