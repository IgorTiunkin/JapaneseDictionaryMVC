package com.phantom.japanese_dictionary_mvc.services;

import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizResult;
import com.phantom.japanese_dictionary_mvc.repositories.QuizResultsRepository;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional (readOnly = true)
public class QuizResultsService {
    private final QuizResultsRepository quizResultsRepository;

    public QuizResultsService(QuizResultsRepository quizResultsRepository) {
        this.quizResultsRepository = quizResultsRepository;
    }

    public List<QuizResult> getQuizResultsByUser (Person user) {
        return quizResultsRepository.findAllByUser(user);
    }

    @Transactional (readOnly = false)
    public void saveQuizResult(QuizResult quizResult) {
        quizResultsRepository.save(quizResult);
    }
}
