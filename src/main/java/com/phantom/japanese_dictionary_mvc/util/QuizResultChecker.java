package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizResultChecker {

    public int getNumberOfRightAnswers(AnswerDto answerDto, List<QuizTask> quizTaskList) {
        int counter = 0;
        List <Answer> userAnswers = answerDto.getAnswers();
        for (int i = 0; i < userAnswers.size(); i++) {
            if (userAnswers.get(i).getAnswer().equals(quizTaskList.get(i).getRightAnswer())) counter++;
        }
        return counter;
    }
}
