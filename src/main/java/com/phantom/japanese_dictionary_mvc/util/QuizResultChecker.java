package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizResultChecker {
    private final static Logger LOGGER = LoggerFactory.getLogger(QuizResultChecker.class);

    public int getNumberOfRightAnswers(List<Answer> userAnswersForCheck, List<QuizTask> quizTaskList) {
        LOGGER.trace("Checking answers: Modified answers = {}",
                userAnswersForCheck);

        int counter = 0;
        for (int i = 0; i < quizTaskList.size(); i++) {
            if (userAnswersForCheck.get(i).getAnswer().equals(quizTaskList.get(i).getRightAnswer())) counter++;
        }
        return counter;
    }


    public List<Answer> createUserAnswersForCheck(List<QuizTask> quizTaskList, List<Answer> acceptedUserAnswers) {
        LOGGER.trace("Checking answers: input answers = {}; tasks = {} ",
                acceptedUserAnswers, quizTaskList);
        List <Answer> userAnswersForCheck = new ArrayList<>(quizTaskList.size());
        int acceptedUserAnswersSize = 0;
        String stubAnswer = "-1";
        if (acceptedUserAnswers != null) {
            acceptedUserAnswersSize = acceptedUserAnswers.size();
            userAnswersForCheck.addAll(acceptedUserAnswers);
            //replace null answers with stub
            for (int i = 0; i < acceptedUserAnswersSize; i++) {
                if (userAnswersForCheck.get(i)==null
                        || userAnswersForCheck.get(i).getAnswer()==null)
                {userAnswersForCheck.set(i, new Answer(stubAnswer));}
            }
        }
        for (int i = acceptedUserAnswersSize; i < quizTaskList.size(); i++) {
            userAnswersForCheck.add(new Answer(stubAnswer));
        }
        return userAnswersForCheck;
    }


    public QuizResult createQuizResultForSave(int numberOfRightAnswers, List<QuizTask> quizTasks,
                                              List<Answer> userAnswers, Person currentUser) {
        QuizResult quizResult = new QuizResult();
        quizResult.setNumberOfRightAnswers(numberOfRightAnswers);
        quizResult.setNumberOfTasks(quizTasks.size());
        quizResult.setUser(currentUser);
        quizResult.setFailedQuizTasks(getFailedTasksFromResult(quizTasks, userAnswers,quizResult));
        return quizResult;
    }

    private List <FailedQuizTask> getFailedTasksFromResult(List<QuizTask> quizTasks,
                                                           List<Answer> userAnswers,
                                                           QuizResult quizResult) {
        List <FailedQuizTask> failedQuizTasks = new ArrayList<>();
        for (int i = 0; i < quizTasks.size(); i++) {
            if (!quizTasks.get(i).getRightAnswer().equals(userAnswers.get(i).getAnswer())) {
                String failedQuestion = quizTasks.get(i).getQuestion();
                failedQuizTasks.add(new FailedQuizTask(quizResult, failedQuestion));
            }
        }
        return failedQuizTasks;
    }
}
