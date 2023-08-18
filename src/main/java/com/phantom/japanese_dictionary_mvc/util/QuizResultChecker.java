package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.AnswerDto;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizResultChecker {
    private final static Logger LOGGER = LoggerFactory.getLogger(QuizResultChecker.class);

    public int getNumberOfRightAnswers(AnswerDto answerDto, List<QuizTask> quizTaskList) {
        //todo
        //проверить еще раз на свежую голову
        List<Answer> acceptedUserAnswers = answerDto.getAnswers();
        LOGGER.trace("Checking answers: Input answers = {}; tasks = {} ",
                acceptedUserAnswers, quizTaskList);
        int counter = 0;
        List <Answer> checkingUserAnswers = new ArrayList<>(quizTaskList.size());
        if (acceptedUserAnswers != null) {
            checkingUserAnswers.addAll(acceptedUserAnswers);
            for (int i = acceptedUserAnswers.size(); i < quizTaskList.size(); i++) {
                checkingUserAnswers.add(new Answer("1"));
            }
        } else {
            for (int i = 0; i <quizTaskList.size(); i++) {
                checkingUserAnswers.add(new Answer("1"));
            }
        }
        answerDto.setAnswers(checkingUserAnswers);
        for (int i = 0; i < quizTaskList.size(); i++) {
            if (checkingUserAnswers.get(i)==null
                    || checkingUserAnswers.get(i).getAnswer()==null) checkingUserAnswers.set(i, new Answer("1"));
            //if user skip answer - make stub for answer
            if (checkingUserAnswers.get(i).getAnswer().equals(quizTaskList.get(i).getRightAnswer())) counter++;
        }
        return counter;
    }
}
