package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizResultChecker {

    public int getNumberOfRightAnswers(AnswerDto answerDto, List<QuizTask> quizTaskList) {
        int counter = 0;
        List <Answer> userAnswers = answerDto.getAnswers();
        if (userAnswers == null || userAnswers.isEmpty()) { //if user havent insert anything - make stub
            userAnswers = new ArrayList<Answer>();
            for (int i = 0; i < quizTaskList.size(); i++) {
                userAnswers.add(new Answer("1"));
            }
        }
        answerDto.setAnswers(userAnswers);
        for (int i = 0; i < userAnswers.size(); i++) {

            if (userAnswers.get(i)==null || userAnswers.get(i).getAnswer()==null) userAnswers.set(i, new Answer("1"));
            //if user skip answer - make stub for answer
            if (userAnswers.get(i).getAnswer().equals(quizTaskList.get(i).getRightAnswer())) counter++;
        }
        return counter;
    }
}
