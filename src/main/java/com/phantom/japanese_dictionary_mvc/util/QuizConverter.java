package com.phantom.japanese_dictionary_mvc.util;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
import com.phantom.japanese_dictionary_mvc.requests.QuizRequest;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuizConverter {
    private final NoteService noteService;

    @Autowired
    public QuizConverter(NoteService noteService) {
        this.noteService = noteService;
    }

    public List<QuizTask> getQuizTasks (QuizRequest quizRequest) {
        int numberOfOptions = quizRequest.getNumberOfOptions();
        int numberOfTasks = quizRequest.getNumberOfTasks();
        List <QuizTask> taskList = new ArrayList<>();
        List <Note> randomVariants = noteService.getRandomVariants(numberOfTasks*numberOfOptions);
        //get random variants enough to break into set of tasks
        for (int i = 0; i < numberOfTasks*numberOfOptions; i+=numberOfOptions) {
            QuizTask quizTask = new QuizTask();
            quizTask.setNumber(i/numberOfOptions+1);
            if (quizRequest.getRequestType() == RequestType.TRANSLATION) {
                quizTask.setQuestion(randomVariants.get(i).getTranslation());
                quizTask.setRightAnswer(randomVariants.get(i).getKanji());
                //get question and right answer
                for (int j = i; j < i+numberOfOptions; j++) {
                    quizTask.getOptions().add(randomVariants.get(j).getKanji());
                }
            } else{
                quizTask.setQuestion(randomVariants.get(i).getKanji());
                quizTask.setRightAnswer(randomVariants.get(i).getTranslation());
                //get question and right answer
                for (int j = i; j < i+numberOfOptions; j++) {
                    quizTask.getOptions().add(randomVariants.get(j).getTranslation());
                }
            }
            Collections.shuffle(quizTask.getOptions());
            //add all options and shuffle
            taskList.add(quizTask);
        }
        return taskList;
    }


}
