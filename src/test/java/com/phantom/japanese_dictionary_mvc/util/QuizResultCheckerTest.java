package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.dto.QuizTask;
import com.phantom.japanese_dictionary_mvc.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class QuizResultCheckerTest {

    private final QuizResultChecker quizResultChecker;

    private final String STUB_ANSWER = "-1";

    @Autowired
    QuizResultCheckerTest(QuizResultChecker quizResultChecker) {
        this.quizResultChecker = quizResultChecker;
    }

    @Test
    public void whenUserAnswersNull_thenDefaultCollection () {
        List<QuizTask> mockTask = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            QuizTask quizTask = new QuizTask();
            quizTask.setRightAnswer(STUB_ANSWER);
            mockTask.add(quizTask);
        }
        List <Answer> answers = quizResultChecker.createUserAnswersForCheck(mockTask, null);
        for (int i = 0; i < 10;i++) {
            assertEquals(STUB_ANSWER, answers.get(i).getAnswer());
        }

    }


    @Test
    public void whenPartialList_thenNoExceptions () {
        List <QuizTask> mockTask = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            QuizTask quizTask = new QuizTask();
            quizTask.setRightAnswer(STUB_ANSWER);
            mockTask.add(quizTask);
        }
        List <Answer> mockUserAnswers = new ArrayList<>();
        mockUserAnswers.add(null);
        mockUserAnswers.add(new Answer("2"));
        mockUserAnswers.add(null);
        mockUserAnswers.add(new Answer("3"));
        mockUserAnswers.add(null);
        mockUserAnswers.add(new Answer("4"));
        List <Answer> answers = quizResultChecker.createUserAnswersForCheck(mockTask, mockUserAnswers);
        assertEquals(STUB_ANSWER,answers.get(0).getAnswer());
        assertEquals("2",answers.get(1).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(2).getAnswer());
        assertEquals("3",answers.get(3).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(4).getAnswer());
        assertEquals("4",answers.get(5).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(6).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(7).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(8).getAnswer());
        assertEquals(STUB_ANSWER,answers.get(9).getAnswer());


    }

    @Test
    public void givenUserAnswers_thenFailedTasks() {
        List <QuizTask> mockQuizTasks = new ArrayList<>();
        List <Answer> mockUserAnswers = new ArrayList<>();
        List <FailedQuizTask> mockFailedQuizTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String testQuestion = String.valueOf(i);
            String testAnswer = String.valueOf(i);
            QuizTask quizTask = new QuizTask();
            quizTask.setQuestion(testQuestion);
            quizTask.setRightAnswer(testAnswer);
            mockQuizTasks.add(quizTask);
            Answer answer = new Answer();
            if (i%2==0) {
                answer.setAnswer(testAnswer);
            } else {
                answer.setAnswer(testAnswer + "1");
                FailedQuizTask failedQuizTask = new FailedQuizTask();
                failedQuizTask.setFailedQuestion(testQuestion);
                mockFailedQuizTasks.add(failedQuizTask);
            }
            mockUserAnswers.add(answer);
        }
        List <FailedQuizTask> realFailedQuizTasks = quizResultChecker.
                createQuizResultForSave(0, mockQuizTasks, mockUserAnswers, null)
                .getFailedQuizTasks();
        assertEquals(mockFailedQuizTasks.size(), realFailedQuizTasks.size());
        for (int i = 0; i < mockFailedQuizTasks.size(); i++) {
            assertEquals(mockFailedQuizTasks.get(i).getFailedQuestion(),
                    realFailedQuizTasks.get(i).getFailedQuestion());
        }

    }

    @Test
    public void when5Correct_then5Correct() {
        List <QuizTask> mockQuizTasks = new ArrayList<>();
        List <Answer> mockUserAnswers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String testQuestion = String.valueOf(i);
            String testAnswer = String.valueOf(i);
            QuizTask quizTask = new QuizTask();
            quizTask.setQuestion(testQuestion);
            quizTask.setRightAnswer(testAnswer);
            mockQuizTasks.add(quizTask);
            Answer answer = new Answer();
            if (i%2==0) {
                answer.setAnswer(testAnswer);
            } else {
                answer.setAnswer(testAnswer + "w");
            }
            mockUserAnswers.add(answer);
        }
        assertEquals(5, quizResultChecker.getNumberOfRightAnswers(mockUserAnswers, mockQuizTasks));
    }

    @Test
    public void whenData_thenQuizResult() {
        int numberOfRightAnswers = 5;
        int numberOfTasks = 19;
        List <QuizTask> mockQuizTasks = new ArrayList<>();
        List <Answer> mockUserAnswers = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            String testQuestion = String.valueOf(i);
            String testAnswer = String.valueOf(i);
            QuizTask quizTask = new QuizTask();
            quizTask.setQuestion(testQuestion);
            quizTask.setRightAnswer(testAnswer);
            mockQuizTasks.add(quizTask);
            Answer answer = new Answer();
            if (i%2==0) {
                answer.setAnswer(testAnswer);
            } else {
                answer.setAnswer(testAnswer + "w");
            }
            mockUserAnswers.add(answer);
        }
        Person person = new Person("username", "password");
        QuizResult quizResultForSave = quizResultChecker.createQuizResultForSave
                (numberOfRightAnswers, mockQuizTasks, mockUserAnswers, person);
        assertEquals(person.getUsername(), quizResultForSave.getUser().getUsername());
        assertEquals(5, quizResultForSave.getNumberOfRightAnswers());
        assertEquals(numberOfTasks, quizResultForSave.getNumberOfTasks());
        assertEquals("1", quizResultForSave.getFailedQuizTasks().get(0).getFailedQuestion());


    }

}